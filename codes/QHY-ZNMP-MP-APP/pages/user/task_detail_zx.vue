<template>
	<view class="container">
		<view class="detail-desc">
			
			<view class="d-header">
				<text>发运数据</text>
			</view>
			<view class="c-list">
				<view class="c-row b-b">
					<text class="tit">总任务箱数</text>
					<view class="con-list">
						<text>{{taskInfo.count}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">已出库箱数</text>
					<view class="con-list">
						<text>{{taskInfo.outNum}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">车牌号</text>
					<view class="con-list">
						<input placeholder="请填写车牌号" name="input" v-model="carNo" @input="carNoInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">箱号</text>
					<view class="con-list">
						<input placeholder="请填写箱号" name="input" v-model="boxNo" @input="boxNoInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">封号</text>
					<view class="con-list">
						<input placeholder="请填写封号" name="input" v-model="titleNo" @input="titleNoInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">毛重(吨)</text>
					<view class="con-list">
						<input placeholder="请填写毛重" name="input" v-model="grossWeight" @input="grossWeightInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">皮重(吨)</text>
					<view class="con-list">
						<input placeholder="请填写皮重" name="input" v-model="skinWeight" @input="skinWeightInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">净重(自动计算)</text>
					<view class="con-list">
						<text>{{numFilter(netWeight)}}</text>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action">
						磅单照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="poundImg != ''" @tap="ViewImage" :data-url="poundImg">
						 <image :src="poundImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="0">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImagePound" v-if="poundImg == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="cu-bar bg-white ">
					<view class="action">
						箱体照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="boxImg != ''" @tap="ViewImage" :data-url="boxImg">
						 <image :src="boxImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="1">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImageBox" v-if="boxImg == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="cu-bar bg-white ">
					<view class="action">
						箱号照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="boxNoImg != ''" @tap="ViewImage" :data-url="boxNoImg">
						 <image :src="boxNoImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="2">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImageBoxNo" v-if="boxNoImg == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="cu-bar bg-white ">
					<view class="action">
						封号照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="titleNoImg != ''" @tap="ViewImage" :data-url="titleNoImg">
						 <image :src="titleNoImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="3">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImageTitleNo" v-if="titleNoImg == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
			</view> 
		</view>
		<view class="cu-modal" :class="modalName=='userModal'?'show':''" @tap="hideModal">
			<scroll-view scroll-y class="cu-dialog" :style="userStyle" @tap.stop="">
				<view class="cu-list menu text-center" >
					<view class="cu-item" v-for="(item,index) in userList" :key="index" @click="mygaipai(item.id)">
						<label class="flex justify-between align-center flex-sub">
							<view class="flex-sub">{{item.userName}}</view>
						</label>
					</view>
				</view>
			</scroll-view>
		</view>
		<view class="cu-modal" :class="modalName=='workModal'?'show':''" @tap="hideModal">
			<scroll-view scroll-y class="cu-dialog" :style="userStyle" @tap.stop="">
				<view class="cu-list menu text-center" >
					<view class="cu-item" v-for="(item,index) in userList" :key="index" @click="nextPerson(item.id)">
						<label class="flex justify-between align-center flex-sub">
							<view class="flex-sub">{{item.userName}}</view>
						</label>
					</view>
				</view>
			</scroll-view>
		</view>
		<view v-if="taskStatus == 1" class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="save">保存</button>
				<button type="primary" class=" action-btn no-border add-cart-btn" @click="finish">完成</button>
				<button type="primary" class=" action-btn no-border add-cart-btn" @click="gaipai">改派</button>
			</view>
		</view> 
	</view>
</template>

<script>
	import uploadImage from '@/components/ossutil/uploadFile.js';
	import {  
	    mapState 
	} from 'vuex';
	export default {
		data() {
			return {
				taskInfo:[],
				poundImg:'',
				grossWeight:'',
				skinWeight:'',
				netWeight:'',
				carNo:'',
				taskStatus:'',
				userStyle:'',
				modalName:'',
				userList:[],
				taskType:'',
				taskId:'',
				poundImg:'',
				boxImg:'',
				boxNoImg:'',
				titleNoImg:'',
				boxNo:'',
				titleNo:''
			};
		},
		computed: {
			...mapState(['hasLogin','userInfo'])
		},
		onShow() {
		},
		onLoad(options) {
			const that = this
			this.taskId = options.taskId
			this.taskStatus = options.taskStatus
			uni.showLoading({
				title: '正在加载',
				mask:true
			})
			that.$api.request('task', 'getTaskInfoZX', {
				taskId: options.taskId
			}, failres => {
				that.$api.msg(failres.errmsg)
				uni.hideLoading()
			}).then(res => {			
				that.taskInfo = res.data
				that.grossWeight = res.data.grossWeight
				that.skinWeight = res.data.skinWeight
				that.netWeight = res.data.netWeight
				that.poundImg = res.data.poundImg
				that.boxImg = res.data.boxImg
				that.boxNoImg = res.data.boxNoImg
				that.titleNoImg = res.data.titleNoImg
				that.carNo = res.data.carNo
				that.boxNo = res.data.boxNo
				that.titleNo = res.data.titleNo
				uni.hideLoading()
			})
		},
		
		methods: {
			numFilter (value) {
				if(!value){
					return 0
				}
				// 截取当前数据到小数点后两位
				let realVal = parseFloat(value).toFixed(2)
				return realVal
			},
			ChooseImageBox() {
				var that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'boxImg/',
							result => {
								that.boxImg = result
								uni.hideLoading();
							}
						)
					}
				});
			},
			ChooseImageBoxNo() {
				var that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'boxNoImg/',
							result => {
								that.boxNoImg = result
								uni.hideLoading();
							}
						)
					}
				});
			},
			ChooseImageTitleNo() {
				var that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'titleNoImg/',
							result => {
								that.titleNoImg = result
								uni.hideLoading();
							}
						)
					}
				});
			},
			hideModal(e) {
				this.modalName = null
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
								this.poundImg = "";
							}
							else if(e.currentTarget.dataset.index == 1){
								this.boxImg = "";
							}
							else if(e.currentTarget.dataset.index == 2){
								this.boxNoImg = "";
							}
							else if(e.currentTarget.dataset.index == 3){
								this.titleNoImg = "";
							}
							
						}
					}
				})
			},
			ChooseImagePound() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'poundImg/',
							result => {
								this.poundImg = result
								uni.hideLoading();
							}
						)
					}
				});
			},
			grossWeightInput(e) {
				this.grossWeight = e.detail.value
				if(this.grossWeight && this.skinWeight){
					this.netWeight = this.grossWeight - this.skinWeight
				}
			},
			skinWeightInput(e) {
				this.skinWeight = e.detail.value
				if(this.grossWeight && this.skinWeight){
					this.netWeight = this.grossWeight - this.skinWeight
				}
			},
			carNoInput(e) {
				this.carNo = e.detail.value
			},
			boxNoInput(e) {
				this.boxNo = e.detail.value
			},
			titleNoInput(e) {
				this.titleNo = e.detail.value
			},
			save(){
				const that = this
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				that.$api.request('task', 'saveTaskInfoZX', {
					taskId: that.taskId,
					grossWeight:!that.grossWeight?'':that.grossWeight,
					skinWeight:!that.skinWeight?'':that.skinWeight,
					netWeight:!that.netWeight?'':that.netWeight,
					poundImg:!that.poundImg?'':that.poundImg,
					carNo:!that.carNo?'':that.carNo,
					boxNo:!that.boxNo?'':that.boxNo,
					titleNo:!that.titleNo?'':that.titleNo,
					boxImg:!that.boxImg?'':that.boxImg,
					boxNoImg:!that.boxNoImg?'':that.boxNoImg,
					titleNoImg:!that.titleNoImg?'':that.titleNoImg
				}, failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				}).then(res => {			
					that.$api.msg('保存成功')
					uni.hideLoading()
				})
			},
			finish(){
				const that = this
				// if(!that.grossWeight){
				// 	this.$api.msg('请填写毛重');
				// 	return;
				// }
				// if(!that.skinWeight){
				// 	this.$api.msg('请填写皮重');
				// 	return;
				// }
				if(!that.carNo){
					this.$api.msg('请填写车牌号');
					return;
				}
				if(!that.boxNo){
					this.$api.msg('请填写箱号');
					return;
				}
				if(!that.titleNo){
					this.$api.msg('请填写封号');
					return;
				}
				// if(!that.poundImg){
				// 	this.$api.msg('请上传磅单照片');
				// 	return;
				// }
				if(!that.boxImg){
					this.$api.msg('请上传箱体照片');
					return;
				}
				if(!that.boxNoImg){
					this.$api.msg('请上传箱号照片');
					return;
				}
				if(!that.titleNoImg){
					this.$api.msg('请上传封号照片');
					return;
				}
				uni.showModal({
					  title: '温馨提示',
					  content: '是否指派外勤继续完成装箱进港发运任务',
					  confirmText:"是",
					  cancelText:"否",
					  success: function (res) {
						  if (res.confirm) {
							 uni.showLoading({
							 	title: '正在加载',
								mask:true
							 })
							 that.$api.request('user', 'getUserList', {
								role:'外勤'
							}, failres => {
							 	that.$api.msg(failres.errmsg)
							 	uni.hideLoading()
							 }).then(res => {
							 	that.userList = res.data
							 	if(that.userList.length == 0){
							 		that.$api.msg('暂无外勤信息')
							 	}
							 	else{
							 		var height = that.userList.length * 100
							 		var width = 500
							 		that.userStyle = "height:"+height+"rpx;width:" + width+"rpx"
							 		that.modalName = 'workModal'
							 	}
							 	uni.hideLoading()
							 })
						  } else if (res.cancel) {
							uni.showLoading({
								title: '正在加载',
								mask:true
							})
							that.$api.request('task', 'finishTaskInfoZX', {
								taskId: that.taskId,
								grossWeight:!that.grossWeight?'':that.grossWeight,
								skinWeight:!that.skinWeight?'':that.skinWeight,
								netWeight:!that.netWeight?'':that.netWeight,
								poundImg:!that.poundImg?'':that.poundImg,
								carNo:!that.carNo?'':that.carNo,
								boxNo:!that.boxNo?'':that.boxNo,
								titleNo:!that.titleNo?'':that.titleNo,
								boxImg:!that.boxImg?'':that.boxImg,
								boxNoImg:!that.boxNoImg?'':that.boxNoImg,
								titleNoImg:!that.titleNoImg?'':that.titleNoImg,
								nextUserId: -1
							}, failres => {
								that.$api.msg(failres.errmsg)
								uni.hideLoading()
							}).then(res => {			
								that.$api.msg('完成成功')
								setTimeout(()=>{uni.navigateBack({})},1000);
								uni.hideLoading()
							})
						  }
					  }
				});
				
				
			},
			gaipai(){
				const that = this
				uni.showLoading({
					title: '正在加载'
				})
				that.$api.request('user', 'getUserList', {
					role:'外勤'
				}, failres => {
				 	that.$api.msg(failres.errmsg)
				 	uni.hideLoading()
				 }).then(res => {
					that.userList = res.data
					if(that.userList.length == 0){
						that.$api.msg('暂无外勤信息')
					}
					else{
						var height = that.userList.length * 100
						var width = 500
						that.userStyle = "height:"+height+"rpx;width:" + width+"rpx"
						that.modalName = 'userModal'
					}
					
					uni.hideLoading()
				})
			},
			mygaipai(id){
				var that = this
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				that.$api.request('task', 'gaipai',{
					taskId: that.taskId,
					outPersonId:id
				},failres => {
					that.$api.msg(failres.errmsg)
					that.modalName = null
					uni.hideLoading()
				}).then(res => {		
					that.modalName = null
					uni.navigateBack({
						delta: 2
					})
					uni.hideLoading()
				})
			},
			nextPerson(id){
				var that = this
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				that.$api.request('task', 'finishTaskInfoZX', {
					taskId: that.taskId,
					grossWeight:!that.grossWeight?'':that.grossWeight,
					skinWeight:!that.skinWeight?'':that.skinWeight,
					netWeight:!that.netWeight?'':that.netWeight,
					poundImg:!that.poundImg?'':that.poundImg,
					carNo:!that.carNo?'':that.carNo,
					boxNo:!that.boxNo?'':that.boxNo,
					titleNo:!that.titleNo?'':that.titleNo,
					boxImg:!that.boxImg?'':that.boxImg,
					boxNoImg:!that.boxNoImg?'':that.boxNoImg,
					titleNoImg:!that.titleNoImg?'':that.titleNoImg,
					nextUserId: id
				}, failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				}).then(res => {		
					that.$api.msg('完成成功')
					setTimeout(()=>{uni.navigateBack({})},1000);	
					uni.hideLoading()
				})
			},
		},
	}
</script>

<style lang='scss'>
	.container{
		padding-bottom: 160upx;
	}
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
	/* 底部操作菜单 */
	.page-bottom {
		.action-btn-group {
			.action-btn {
				width: 33.333%;
			}
		}
	}
	@mixin playcenter {
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.xsh-start {
		width: 105rpx;
		height: 105rpx;
		background: #FFFFFF;
		border-radius: 50%;
		font-size: 29rpx;
		color: #4135EB;
		@include playcenter;
		flex-wrap: wrap;
	}
	.x-modal {
		width: 100%;
		.x-m-title {
			width: 100%;
			height: 90rpx;
			padding: 0 38rpx 0 31rpx;
			box-sizing: border-box;
			font-size: 29rpx;
			color: #333333;
			border-bottom: 1px dashed #999;
			@include playcenter;
			justify-content: space-between;
			.xm-t-clear {
				font-size: 25rpx;
				color: #341DB7;
				@include playcenter;
				>image {
					width: 28rpx;
					height: 28rpx;
					display: block;
					margin-right: 8rpx;
				}
			}
		}
		.x-m-con {
			width: 100%;
			padding: 0 31rpx 18rpx;
			margin-top: 5rpx;
			box-sizing: border-box;
		}
	}
	
</style>
