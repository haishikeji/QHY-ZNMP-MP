<template>
	<view class="container">
		<view v-if="tranInfo != undefined" class="introduce-section">
			<view class="price-box">
				<view class="title">{{tranInfo.tranNo}}</view>
				<view class="title-tip">
					<text class="price-tip">¥</text>
					<text class="price">{{tranInfo.price}}</text>
					<text class="price-tip">元/吨</text>
				</view>
			</view>
			<view class="bot-row">
				<view class='cu-tag radius line-orange'>{{tranInfo.sender}}</view>
				<view class='cu-tag radius line-orange'>{{tranInfo.senderPhone}}</view>
			</view>
			
			<view v-if='tranInfo.total' class="bot-row">
				<view class='cu-tag radius line-orange'>剩余量: {{tranInfo.total - tranInfo.tranCount}}吨</view>
				<view class='cu-tag radius line-orange'>总量: {{tranInfo.total}}吨</view>
			</view>
		</view>
		<view v-if="tranInfo != undefined" class="detail-desc">
			<view  class="d-header">
				<text>货源信息</text>
			</view>
			 <view class="c-list">
				 <view class="c-row b-b">
				 	<text class="tit">收货人</text>
				 	<view class="con-list">
				 		<text>{{tranInfo.receiver}}</text>
				 	</view>
				 </view>
				 <view class="c-row b-b">
				 	<text class="tit">收货人电话</text>
				 	<view class="con-list">
				 		<text>{{tranInfo.receiverPhone}}</text>
				 	</view>
				 </view>
				<view v-if="tranInfo.goodsName!='外采'" class="c-row b-b">
					<text class="tit">货名</text>
					<view class="con-list">
						<text>{{tranInfo.goodsName}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">起始地</text>
					<view class="con-list">
						<text>{{tranInfo.startPlace}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">目的地</text>
					<view class="con-list">
						<text>{{tranInfo.endPlace}}</text>
					</view>
				</view>
				<view v-if='tranInfo.total' class="c-row b-b">
					<text class="tit">剩余量</text>
					<view class="con-list">
						<text>{{tranInfo.total-tranInfo.tranCount}}</text>
					</view>
				</view>
			</view>
			<view class="d-header">
				<text>承运人：{{car.driver}}</text>
			</view>
			<view class="c-list">
				<view class="c-row b-b">
					<text class="tit">车牌号</text>
					<view class="con-list">{{car.carNo}}</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">承运人电话</text>
					<view class="con-list">{{car.driverPhone}}</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">价格</text>
					<view class="con-list">{{car.price}}</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action">
						司机身份证(正、反面)
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="personNoImg != ''" @tap="ViewImage" :data-url="personNoImg">
						 <image :src="personNoImg" mode="aspectFit"></image>
						</view>
						<view class="bg-img" v-if="personNoImg1 != ''" @tap="ViewImage" :data-url="personNoImg1">
						 <image :src="personNoImg1" mode="aspectFit"></image>
						</view>
					</view>
				</view>
				<view class="cu-bar bg-white ">
					<view class="action">
						司机驾驶证(主页、副页)
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="driverNoImg != ''" @tap="ViewImage" :data-url="driverNoImg">
						 <image :src="driverNoImg" mode="aspectFit"></image>
						</view>
						<view class="bg-img" v-if="driverNoImg1 != ''" @tap="ViewImage" :data-url="driverNoImg1">
						 <image :src="driverNoImg1" mode="aspectFit"></image>
						</view>
					</view>
				</view>
				<view class="cu-bar bg-white ">
					<view class="action">
						行车证(主页、副页)
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="carNoImg != ''" @tap="ViewImage" :data-url="carNoImg">
						 <image :src="carNoImg" mode="aspectFit"></image>
						</view>
						<view class="bg-img" v-if="carNoImg1 != ''" @tap="ViewImage" :data-url="carNoImg1">
						 <image :src="carNoImg1" mode="aspectFit"></image>
						</view>
					</view>
				</view>
			</view> 
			
		</view>
		<view class="c-list">
			<view class="c-row b-b">
				<text class="tit">拒接原因</text>
				<view class="con-list">
					<input placeholder="请填写拒接原因" name="input" v-model="reason" @input="reasonInput"></input>
				</view>
			</view>
		</view>
		<view v-if="car.status == 13" class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="jiedan">接单</button>
				<button type="primary" class=" action-btn no-border add-cart-btn" @click="jujie">拒接</button>
				<!-- <button type="primary" class=" action-btn no-border add-cart-btn" @click="tuihui">退回司机</button> -->
			</view>
		</view>
	
	</view>
</template>

<script>
	import uploadImage from '@/components/ossutil/uploadFile.js';
	import uniNumberBox from '@/components/uni-number-box.vue';
	import {  
        mapState 
    } from 'vuex';
	export default {
		components: {
			uniNumberBox
		},
		data() {
			return {
				car: {
					id:'',
					tranNo:'',
					tranCount: 0,
					price:0,
					driverPhone:'',
				},
				tranInfo:undefined,
				driver:'',
				driverPhone:'',
				price:0,
				carNo:'',
				tranCount: 0,
				sendText:'获取验证码',
				sendDisabled: false,
				verifyCode:'',
				modalName: null,
				tranNo: '请选择货源单号',
				exsitCount:0,
				personNoImg:'',
				personNoImg1:'',
				driverNoImg:'',
				driverNoImg1:'',
				carNoImg:'',
				carNoImg1:'',
				taskId:'',
				reason:'',
				sendCarNo:'',
				taskStatus:''
			};
		},
		onShow() {
		},
		onLoad(options) {
			const that = this
			that.taskId = options.taskId
			that.sendCarNo = options.sendCarNo
			that.taskStatus = options.taskStatus
			uni.showLoading({
				title: '正在加载',
						mask:true
			})
			that.$api.request('tran', 'getCarDetailInfoBySendCarNo', {
				sendCarNo: options.sendCarNo
			}, failres => {
				that.$api.msg(failres.errmsg)
				uni.hideLoading()
			}).then(res => {
				that.car = res.data
				that.personNoImg = that.car.personNoImg
				that.personNoImg1 = that.car.personNoImg1
				that.driverNoImg = that.car.driverNoImg
				that.driverNoImg1 = that.car.driverNoImg1
				that.carNoImg = that.car.carNoImg
				that.carNoImg1 = that.car.carNoImg1
				that.tranInfo = res.data.tranInfo
				uni.hideLoading()
			})
		},
		computed: {
			...mapState(['hasLogin','userInfo'])
		},
		methods: {
			reasonInput(e) {
				this.reason = e.detail.value
			},
			jiedan(){
				const that = this
				uni.showLoading({
					title: '正在加载',
						mask:true
				})
				that.$api.request('tran', 'jiedan', {
					id: that.car.id,
					taskId:that.taskId
				}, failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				})
				.then(res => {		
					uni.hideLoading()
					if(that.car.memo == "外采(不含运费)"){
					
						var sendCarNo = that.sendCarNo
						var taskStatus = that.taskStatus
						var taskId = that.taskId
						var taskType = "外采任务"
						uni.navigateTo({
							url: `/pages/user/task_detail_buy?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
						})
					
					}
					else{
					var sendCarNo = that.sendCarNo
					var taskStatus = that.taskStatus
					var taskId = that.taskId
					var taskType = "发运任务"
					uni.navigateTo({
						url: `/pageA/pages/task_detail?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
					})
				 }
				})
			},
			jujie(){
				const that = this
				if(!that.reason){
					this.$api.msg('请填写拒接原因');
					return;
				}
				uni.showLoading({
					title: '正在加载',
						mask:true
				})
				that.$api.request('tran', 'jujie', {
					id: that.car.id,
					taskId:that.taskId,
					reason:that.reason
				}, failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				}).then(res => {			
					that.$api.msg('拒接成功')
					uni.hideLoading()
					setTimeout(()=>{uni.navigateBack()},1000);
				})
			},
			tuihui(){
				const that = this
				uni.showLoading({
					title: '正在加载',
						mask:true
				})
				that.$api.request('tran', 'tuihui', {
					id: that.car.id,
					taskId:that.taskId
				}, failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				}).then(res => {			
					that.$api.msg('退回成功')
					uni.hideLoading()
					setTimeout(()=>{uni.navigateBack()},1000);
				})
			},
			ChooseImagePerson() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'personNoImg/',
							result => {
							 if (this.personNoImg.length != 0) {
							 	this.personNoImg1 = result
							 } else {
							 	this.personNoImg = result
							 }
							 uni.hideLoading();
							}
						)
					}
				});
			},
			ChooseImageDriver() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'driverNoImg/',
							result => {
								if (this.driverNoImg.length != 0) {
									this.driverNoImg1 = result
								} else {
									this.driverNoImg = result
								}
								uni.hideLoading();
							}
						)
					}
				});
			},
			ChooseImageCar() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'carNoImg/',
							result => {
								if (this.carNoImg.length != 0) {
									this.carNoImg1 = result
								} else {
									this.carNoImg = result
								}
								uni.hideLoading();
							}
						)
					}
				});
			},
			ViewImage(e) {
				var img = [];
				img = e.currentTarget.dataset.url.split(' ')
				uni.previewImage({
					current:0,
					urls: img
				});
			},
			DelImg(e) {
				uni.showModal({
					title: '提示',
					content: '确定要删除该照片吗？',
					cancelText: '取消',
					confirmText: '确定',
					success: res => {
						if (res.confirm) {
							if(e.currentTarget.dataset.index == 0){
								this.personNoImg = "";
							}
							else if(e.currentTarget.dataset.index == 1){
								this.personNoImg1 = "";
							}
							else if(e.currentTarget.dataset.index == 2){
								this.driverNoImg = "";
							}
							else if(e.currentTarget.dataset.index == 3){
								this.driverNoImg1 = "";
							}
							else if(e.currentTarget.dataset.index == 4){
								this.carNoImg = "";
							}
							else if(e.currentTarget.dataset.index == 5){
								this.carNoImg1 = "";
							}
							
						}
					}
				})
			},
			
		},

	}
</script>


<style lang='scss' scoped>
	.container {
		background: $page-color-base;
		padding-bottom: 160upx;
	}
	.icon-you {
		font-size: $font-base + 2upx;
		color: #888;
	}

	.carousel {
		height: 722upx;
		position: relative;

		swiper {
			height: 100%;
		}

		.image-wrapper {
			width: 100%;
			height: 100%;
		}

		.swiper-item {
			display: flex;
			justify-content: center;
			align-content: center;
			height: 750upx;
			overflow: hidden;

			image {
				width: 100%;
				height: 100%;
			}
		}

	}
	.c-list {
		font-size: $font-sm + 2upx;
		color: $font-color-base;
		background: #fff;

		.c-row {
			display: flex;
			align-items: center;
			padding: 20upx 30upx;
			position: relative;
		}

		.tit {
			width: 220upx;
		}

		.con {
			flex: 1;
			color: $font-color-dark;

			.selected-text {
				margin-right: 10upx;
			}
		}

		.bz-list {
			height: 40upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;

			text {
				display: inline-block;
				margin-right: 30upx;
			}
		}

		.con-list {
			flex: 1;
			display: flex;
			flex-direction: column;
			color: $font-color-dark;
			line-height: 40upx;
			text-align: right;
			padding-right: 20upx;
		}

		.red {
			color: $uni-color-primary;
		}
	}

	/* 评价 */
	.eva-section {
		display: flex;
		flex-direction: column;
		padding: 20upx 30upx;
		background: #fff;
		margin-top: 16upx;

		.e-header {
			display: flex;
			align-items: center;
			height: 70upx;
			font-size: $font-sm + 2upx;
			color: $font-color-light;

			.tit {
				font-size: $font-base + 2upx;
				color: $font-color-dark;
				margin-right: 4upx;
			}

			.tip {
				flex: 1;
				text-align: right;
			}

			.icon-you {
				margin-left: 10upx;
			}
		}
	}

	.eva-box {
		display: flex;
		padding: 20upx 0;

		.portrait {
			flex-shrink: 0;
			width: 80upx;
			height: 80upx;
			border-radius: 100px;
		}

		.right {
			flex: 1;
			display: flex;
			flex-direction: column;
			font-size: $font-base;
			color: $font-color-base;
			padding-left: 26upx;

			.con {
				font-size: $font-base;
				color: $font-color-dark;
				padding: 20upx 0;
			}

			.bot {
				display: flex;
				justify-content: space-between;
				font-size: $font-sm;
				color: $font-color-light;
			}
		}
	}

	/*  详情 */
	.detail-desc {
		background: #fff;
		margin-top: 16upx;
		width: 750upx;

		.d-header {
			display: flex;
			justify-content: center;
			align-items: center;
			height: 80upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			position: relative;

			text {
				padding: 0 20upx;
				background: #fff;
				position: relative;
				z-index: 1;
			}

			&:after {
				position: absolute;
				left: 50%;
				top: 50%;
				transform: translateX(-50%);
				width: 300upx;
				height: 0;
				content: '';
				border-bottom: 1px solid #ccc;
			}
		}
	}

	/* 规格选择弹窗 */
	.attr-content {
		padding: 10upx 30upx;

		.a-t {
			display: flex;

			image {
				width: 170upx;
				height: 170upx;
				flex-shrink: 0;
				margin-top: -40upx;
				border-radius: 8upx;
				;
			}

			.right {
				display: flex;
				flex-direction: column;
				padding-left: 24upx;
				font-size: $font-sm + 2upx;
				color: $font-color-base;
				line-height: 42upx;

				.price {
					font-size: $font-lg;
					color: $uni-color-primary;
					margin-bottom: 10upx;
				}

				.selected-text {
					margin-right: 10upx;
				}
			}
		}

		.attr-list {
			display: flex;
			flex-direction: column;
			font-size: $font-base + 2upx;
			color: $font-color-base;
			padding-top: 30upx;
			padding-left: 10upx;
		}

		.item-list {
			padding: 30upx 0 0;
			display: flex;
			flex-wrap: wrap;

			text {
				display: flex;
				align-items: center;
				justify-content: center;
				background: #eee;
				margin-right: 20upx;
				margin-bottom: 20upx;
				border-radius: 100upx;
				min-width: 60upx;
				height: 60upx;
				padding: 0 20upx;
				font-size: $font-base;
				color: $font-color-dark;
			}

			.selected {
				background: #fbebee;
				color: $uni-color-primary;
			}
		}
	}

	/*  弹出层 */
	.popup {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		z-index: 99;

		&.show {
			display: block;

			.mask {
				animation: showPopup 0.2s linear both;
			}

			.layer {
				animation: showLayer 0.2s linear both;
			}
		}

		&.hide {
			.mask {
				animation: hidePopup 0.2s linear both;
			}

			.layer {
				animation: hideLayer 0.2s linear both;
			}
		}

		&.none {
			display: none;
		}

		.mask {
			position: fixed;
			top: 0;
			width: 100%;
			height: 100%;
			z-index: 1;
			background-color: rgba(0, 0, 0, 0.4);
		}

		.layer {
			position: fixed;
			z-index: 99;
			bottom: 0;
			width: 100%;
			min-height: 40vh;
			border-radius: 10upx 10upx 0 0;
			background-color: #fff;

			.btn {
				height: 66upx;
				line-height: 66upx;
				border-radius: 100upx;
				background: $uni-color-primary;
				font-size: $font-base + 2upx;
				color: #fff;
				margin: 30upx auto 20upx;
			}
		}

		@keyframes showPopup {
			0% {
				opacity: 0;
			}

			100% {
				opacity: 1;
			}
		}

		@keyframes hidePopup {
			0% {
				opacity: 1;
			}

			100% {
				opacity: 0;
			}
		}

		@keyframes showLayer {
			0% {
				transform: translateY(120%);
			}

			100% {
				transform: translateY(0%);
			}
		}

		@keyframes hideLayer {
			0% {
				transform: translateY(0);
			}

			100% {
				transform: translateY(120%);
			}
		}
	}
	/* 底部操作菜单 */
	.page-bottom {
		.action-btn-group {
			.action-btn {
				width: 33.3333%;
			}
		}
	}
	.rich-img {
		width: 100%;
		height: auto;
		margin: 0;
		padding: 0;
		line-height: 0px;
	}
	button::after {
		border: none;
	}
	/* 销售信息 */
	.introduce-section {
		background: #fff;
		padding: 20upx 30upx;
		padding-bottom: 100upx;
		.guess-item {
			padding-bottom: 20upx;
			border-bottom: 1px solid #ccc;
		}
		.title {
			font-size: 28upx;
			color: $font-color-dark;
			font-weight:bold;
			height: 50upx;
			line-height: 50upx;
			flex:2.5;
		}
		.title-tip {
			flex:1;
		}
		.price-box {
			display: flex;
			align-items: baseline;
			height: 70upx;
			padding: 10upx 0;
			font-size: 26upx;
			color: $uni-color-primary;
		}
	
		.price {
			font-size: $font-lg + 2upx;
		}
	
		.m-price {
			margin: 0 12upx;
			color: $font-color-light;
			text-decoration: line-through;
		}
	
		.coupon-tip {
			align-items: center;
			padding: 4upx 10upx;
			background: $uni-color-primary;
			font-size: $font-sm;
			color: #fff;
			border-radius: 6upx;
			line-height: 1;
			transform: translateY(-4upx);
		}
	
		.bot-row {
			display: flex;
			align-items: center;
			height: 50upx;
			font-size: $font-sm;
			color: $font-color-light;
	
			view {
				flex: 1;
			}
		}
	}
</style>
