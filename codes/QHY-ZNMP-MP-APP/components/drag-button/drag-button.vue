<template>
	<view>
		<view
			id="_drag_button"
			class="drag"
			:style="'right: ' + right + 'px; top:' + top + 'px;background:'+background+';box-shadow:'+boxshadow"
			@touchstart="touchstart"
			@touchmove.stop.prevent="touchmove"
			@touchend="touchend"
			@click.stop.prevent="click"
			:class="{transition: isDock && !isMove }"
		>
		<image v-if='isIcon' class='side-bg' src='https://taohaoliang.oss-cn-beijing.aliyuncs.com/tmp/side-bg.png'></image>
		<view style='position: relative;z-index:2;text-align:center;' :class="icon"><view><image style='width:11px;height:11px;' src="https://taohaoliang.oss-cn-beijing.aliyuncs.com/tmp/add.png" mode=""></image></view>{{text}}</view> 
		</view>
	</view>
</template>

<script>
	export default {
		name: 'drag-button',
		props: {
			isDock:{
				type: Boolean,
				default: false
			},
			isIcon:{
				type: Boolean,
				default: false
			},
			existTabBar:{
				type: Boolean,
				default: false
			},
			text:'',
			icon:'',
			location:'',
			background:{
				type: String,
				default: 'transparent'
			},
			boxshadow:{
				type: String,
				default: '0 0 6rpx transparent'
			},
		},
		data() {
			return {
				top:0,
				right:0,
				width: 0,
				height: 0,
				offsetWidth: 0,
				offsetHeight: 0,
				windowWidth: 0,
				windowHeight: 0,
				isMove: true,
				edge: 10
			}
		},
		mounted() {
			const sys = uni.getSystemInfoSync();
			this.windowWidth = sys.windowWidth;
			this.windowHeight = sys.windowHeight;
			
			// #ifdef APP-PLUS
				this.existTabBar && (this.windowHeight -= 50);
			// #endif
			if (sys.windowTop) {
				this.windowHeight += sys.windowTop;
			}
			const query = uni.createSelectorQuery().in(this);
			query.select('#_drag_button').boundingClientRect(data => {
				this.width = data.width;
				this.height = data.height;
				this.offsetWidth = data.width / 2;
				this.offsetHeight = data.height / 2;
				this.top = this.windowHeight - this.height - this.edge - Number(this.location) - 80;
			}).exec();
		},
		methods: {
			click() {
				this.$emit('btnClick');
			},
			touchstart(e) {
				this.$emit('btnTouchstart');
			},
			touchmove(e) {
				// 单指触摸
				if (e.touches.length !== 1) {
					return false;
				}
				
				this.isMove = true;
				
				this.left = e.touches[0].clientX - this.offsetWidth;
				
				let clientY = e.touches[0].clientY - this.offsetHeight;
				// #ifdef H5
					clientY += this.height;
				// #endif
				let edgeBottom = this.windowHeight - this.height - this.edge;

				// 上下触及边界
				if (clientY < this.edge) {
					this.top = this.edge;
				} else if (clientY > edgeBottom) {
					this.top = edgeBottom;
				} else {
					this.top = clientY
				}
				
			},
			touchend(e) {
				if (this.isDock) {
					let edgeRigth = this.windowWidth - this.width - this.edge;
					
					if (this.left < this.windowWidth / 2 - this.offsetWidth) {
						this.left = this.edge;
					} else {
						this.left = edgeRigth;
					}
					
				}
				
				this.isMove = false;
				
				this.$emit('btnTouchend');
			},
		}}
</script>

<style lang="scss">
	.side-bg{
		position:absolute;
		width: 60px;
		height: 38px;
		z-index: 1;
	}
	.drag {
		position:relative;
		display: flex;
		justify-content: center;
		align-items: center;
		color: $uni-text-color-inverse;
		width: 60px;
		height: 38px;
		background:transparent;
		font-size: $uni-font-size-sm;
		position: fixed;
		z-index: 999999;
		
		&.transition {
			transition: left .3s ease,top .3s ease;
		}
	}
	
</style>
