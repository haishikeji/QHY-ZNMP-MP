<template>
  <view :class="['lb-picker', inline ? 'lb-picker-inline' : '']">
    <view class="lb-picker-mask"
      v-show="visible && !inline"
      :style="{ 'background-color': maskColor }"
      @tap.stop="handleMaskTap"
      @touchmove.stop.prevent="moveHandle">
    </view>
    <view :class="['lb-picker-container', visible ? 'lb-picker-toggle' : '']"
      :style="{ borderRadius: `${radius} ${radius} 0 0` }">
      <view v-if="showHeader"
        class="lb-picker-header"
        :style="{
          height: pickerHeaderHeight,
          'line-height': pickerHeaderHeight
        }">
        <view class="lb-picker-action lb-picker-left">
          <view class="lb-picker-action-cancel"
            @tap.stop="handleCancel">
            <slot v-if="$slots['cancel-text']"
              name="cancel-text"> </slot>
            <view v-else
              class="action-cancel-text"
              :style="{ color: cancelColor }" style="font-size: 28rpx;">
              {{ cancelText }}
            </view>
          </view>
        </view>

        <view class="lb-picker-action lb-picker-center"
          v-if="$slots['action-center']">
          <slot name="action-center"></slot>
        </view>

        <view class="lb-picker-action lb-picker-right">
          <view class="lb-picker-action-confirm"
            @tap.stop="handleConfirm">
            <slot v-if="$slots['confirm-text']"
              name="confirm-text"> </slot>
            <view v-else
              class="action-confirm-text"
               style="font-size: 28rpx;color: #FA6400;">
              {{ confirmText }}
            </view>
          </view>
        </view>
      </view>
      <view class="lb-picker-content"
        :style="{ height: pickerContentHeight }">
        <!-- loading -->
        <view v-if="loading"
          class="lb-picker-loading">
          <slot name="loading">
            <view class="lb-picker-loading-img"></view>
          </slot>
        </view>

        <!-- 暂无数据 -->
        <view v-if="isEmpty && !loading"
          class="lb-picker-empty">
          <slot name="empty">
            <text class="lb-picker-empty-text"
              :style="{ color: emptyColor }">
              {{ emptyText }}
            </text>
          </slot>
        </view>

        <!-- 单选 -->
        <selector-picker v-if="mode === 'selector' && !loading && !isEmpty"
          :value="value"
          :list="list"
          :props="pickerProps"
          :height="pickerContentHeight"
          :inline="inline"
          @change="handleChange">
        </selector-picker>

        <!-- 多列联动 -->
        <multi-selector-picker v-if="mode === 'multiSelector' && !loading && !isEmpty"
          :value="value"
          :list="list"
          :level="level"
          :visible="visible"
          :props="pickerProps"
          :height="pickerContentHeight"
          :inline="inline"
          @change="handleChange">
        </multi-selector-picker>

        <!-- 非联动选择 -->
        <unlinked-selector-picker v-if="mode === 'unlinkedSelector' && !loading && !isEmpty"
          :value="value"
          :list="list"
          :visible="visible"
          :props="pickerProps"
          :height="pickerContentHeight"
          :inline="inline"
          @change="handleChange">
        </unlinked-selector-picker>
      </view>
    </view>
  </view>
</template>

<script>
const defaultProps = {
  label: 'label',
  value: 'value',
  children: 'children'
}
import { getIndicatorHeight } from './utils'
import SelectorPicker from './pickers/selector-picker'
import MultiSelectorPicker from './pickers/multi-selector-picker'
import UnlinkedSelectorPicker from './pickers/unlinked-selector-picker'
const indicatorHeight = getIndicatorHeight()
export default {
  components: {
    SelectorPicker,
    MultiSelectorPicker,
    UnlinkedSelectorPicker
  },
  props: {
    value: [String, Number, Array],
    list: Array,
    mode: {
      type: String,
      default: 'selector'
    },
    level: {
      type: Number,
      default: 1
    },
    props: {
      type: Object
    },
    cancelText: {
      type: String,
      default: '取消'
    },
    cancelColor: String,
    confirmText: {
      type: String,
      default: '确定'
    },
    confirmColor: '#FA6400',
    canHide: {
      type: Boolean,
      default: true
    },
    emptyColor: String,
    emptyText: {
      type: String,
      default: '暂无数据'
    },
    radius: String,
    columnNum: {
      type: Number,
      default: 5
    },
    loading: Boolean,
    closeOnClickMask: {
      type: Boolean,
      default: true
    },
    maskColor: {
      type: String,
      default: 'rgba(0, 0, 0, 0.4)'
    },
    dataset: Object,
    inline: Boolean,
    showHeader: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      visible: false,
      myValue: this.value,
      picker: {},
      pickerProps: Object.assign({}, defaultProps, this.props),
      pickerHeaderHeight: indicatorHeight + 'px',
      pickerContentHeight: indicatorHeight * this.columnNum + 'px'
    }
  },
  computed: {
    isEmpty () {
      if (!this.list) return true
      if (this.list && !this.list.length) return true
      return false
    }
  },
  methods: {
    show () {
      if (this.inline) return
      this.visible = true
    },
    hide () {
      if (this.inline) return
      this.visible = false
    },
    handleCancel () {
      this.$emit('cancel', this.picker)
      if (this.canHide && !this.inline) {
        this.hide()
      }
    },
    handleConfirm () {
      if (this.isEmpty) {
        this.$emit('confirm', null)
        this.hide()
      } else {
        const picker = JSON.parse(JSON.stringify(this.picker))
        this.myValue = picker.value
        this.$emit('confirm', this.picker)
        if (this.canHide) this.hide()
      }
    },
    handleChange ({ value, item, index, change }) {
      this.picker.value = value
      this.picker.item = item
      this.picker.index = index
      this.picker.change = change
      this.picker.dataset = this.dataset || {}
      this.$emit('change', this.picker)
    },
    handleMaskTap () {
      if (this.closeOnClickMask) {
        this.visible = false
        this.$emit('visibleCityPicker', false)
      }
    },
    moveHandle () {}
  },
  watch: {
    value (newVal) {
      this.myValue = newVal
    },
    myValue (newVal) {
      this.$emit('input', newVal)
    },
    visible (newVisible) {
      if (newVisible) {
        this.$emit('show')
      } else {
        this.$emit('hide')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import "./style/picker.scss";
</style>
