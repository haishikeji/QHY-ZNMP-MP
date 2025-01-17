<template>
	<view>
		<view v-for='(item,index) in cardList' class="">
			<view style='margin:10px;' class='flex justify-space-between'>
				<view class="flex">
					<view style="margin-right: 20rpx;">
						{{item.certificateName}}
					</view>
					<view class="">
						<view v-if='item.personNo'>{{item.personNo1}}</view>
						<view v-if='item.bankNo'>{{item.bankNo1}}</view>
					</view>
				</view>
				<view class="flex">

					<view style='margin:0 10rpx;' class='finished' v-if='item.personNo' @click='clip(0,item)'>
						复制号码
					</view>

					<view class='finished' v-if='item.bankNo' @click='clip(1,item)'>
						复制号码
					</view>
					<view :class='countdownTime==0?"finished":"unfinished"' @click='imageOCR(item,index)' style="margin-left: 20rpx;">{{text}}
					</view>
				</view>


			</view>
			<view style='text-align:center;' class="">
				<image @click="click(item)" style='width:96%;height:190px;border-radius:20rpx;'
					:src="item.certificateImage" mode="aspectFill"></image>
			</view>

		</view>
		<u-toast ref="uToast"></u-toast>
		<u-picker :immediateChange="true" @cancel='show=false' @confirm='pickerConfirm' title='识别类型' :show="show"
			:columns="columns"></u-picker>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				cardList: [],
				show: false,
				countdownTime: 0,
				currectData: {},
				index: 0,
				columns: [
					['识别身份证号', '识别银行卡号']
				],
				text: '识别号码'
			}
		},
		onLoad(options) {
			console.log(11111)
			this.cardList = JSON.parse(options.dataList);
			console.log(this.cardList, 11111111)
		},
		methods: {
			click(item) {
				console.log(111111111)
				uni.previewImage({
					current: 0, // 当前显示图片的索引值
					urls: [item], // 需要预览的图片列表，photoList要求必须是数组
					loop: true, // 是否可循环预览
				})
			},
			clip(status, item) {
				uni.setClipboardData({
					data: status == 1 ? item.bankNo : item.personNo, // e是你要保存的内容
					success: function() {
						uni.showToast({
							title: '复制成功',
							icon: 'none'
						})
					}
				})
			},
			imageOCR(item, index) {
				this.currectData = item
				this.index = index
				this.show = true
			},
			getCountdownTime() {
				this.countdownTime = 60
				let timer = setInterval(() => {
					this.countdownTime--;
					this.text = '识别号码（' + this.countdownTime + 's）'
					if (this.countdownTime < 1) {
						clearInterval(timer)
						this.countdownTime = 0
						this.text = '识别号码'

					}
				}, 1000)
			},
			pickerConfirm(e) {

				this.show = false

				if (this.countdownTime == 0) {
					this.getCountdownTime()
					uni.showLoading({
						title: '数据加载中',
						mask: true
					})
					var type = '',
						type1 = ''
					if (e.value[0] == '识别身份证号') {
						type = 'admin.unimall.certificateManagementInfo'
						type1 = 'personShibie'
					} else if (e.value[0] == '识别银行卡号') {
						type = 'admin.unimall.certificateManagementInfo'
						type1 = 'bankShibie'
					}
					this.$request.baseRequest(type, type1, {
						certificateImage: this.currectData.certificateImage
					}, failres => {
						console.log('res+++++', failres.errmsg)
						uni.hideLoading()
						uni.showToast({
							icon: "none",
							title: failres.errmsg,
							duration: 3000
						});
					}).then(res => {
						uni.hideLoading()
						uni.showToast({
							icon: "success",
							title: '识别成功！',
							duration: 2000
						});
						if (e.value[0] == '识别身份证号') {
							this.cardList[this.index].personNo = res.data.recPersonNo
							this.cardList[this.index].personNo1 = '*' + (this.cardList[this.index].personNo
								.substring(this.cardList[this.index].personNo.length - 4))

						} else if (e.value[0] == '识别银行卡号') {
							this.cardList[this.index].bankNo = res.data.bankNo
							this.cardList[this.index].bankNo1 = '*' + (this.cardList[this.index].bankNo.substring(
								this.cardList[this.index].bankNo.length - 4))
						}
						this.credentialsShow = true
					})
				}
				console.log(e)

			},
		}
	}
</script>

<style lang="scss" scoped>
	.finished {
		padding: 10rpx 14rpx;
		text-align: center;
		background: #112253;
		border-radius: 10rpx;
		font-size: 28rpx;
		font-weight: bold;
		color: #FFFFFF;
	}

	.unfinished {
		padding: 10rpx 14rpx;
		text-align: center;
		background: rgba(17, 34, 83, 0.3);
		border-radius: 10rpx;
		font-size: 28rpx;
		font-weight: bold;
		color: #FFFFFF;
	}
</style>