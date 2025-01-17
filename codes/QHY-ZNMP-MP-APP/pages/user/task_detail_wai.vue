<template>
	<view :class="showAutograph?'indexFixed container':'container '" >
		<view class="detail-desc">
			<view class="d-header">
				<text>派车单</text>
			</view>
			 <view class="c-list">
				<view class="c-row b-b">
					<text class="tit">派车单号</text>
					<view class="con-list">
						<text>{{purchaseInfo.sendCarNo}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">出发地</text>
					<view class="con-list">
						<text>{{purchaseInfo.startPlace}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">目的地</text>
					<view class="con-list">
						<text>{{purchaseInfo.endPlace}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">车牌号</text>
					<view class="con-list">
						<text>{{purchaseInfo.carNo}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">司机</text>
					<view class="con-list">
						<text>{{purchaseInfo.driver}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">司机电话</text>
					<view class="con-list">
						<text>{{purchaseInfo.driverPhone}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">运价</text>
					<view class="con-list">
						<text>{{purchaseInfo.price}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">发运净重</text>
					<view class="con-list">
						<text>{{purchaseInfo.netWeight}}</text>
					</view>
				</view>
				<view v-if='purchaseInfo.num' class="c-row b-b">
					<text class="tit">任务量</text>
					<view class="con-list">
						<text>{{numFilter(purchaseInfo.num)}}</text>
					</view>
				</view>
			</view>
			<view class="d-header">
				<text>入库数据</text>
			</view>
			<view class="c-list">
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
				<view class="cu-bar bg-white ">
					<view class="action">
						磅单照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="poundImg != ''" @tap="ViewImage" :data-url="poundImg">
						 <image :src="poundImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" >
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImage" v-if="poundImg == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
			</view> 
			<view class="d-header">
				<text>到货粮质</text>
			</view>
			<view class="c-list">
				<view class="c-row b-b">
					<text class="tit">水分(%){{'<='}}</text>
					<view class="con-list">
						<input placeholder="请填写水分" name="input" v-model="waterContent" @input="waterContentInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">容重(克/升){{'>='}}</text>
					<view class="con-list">
						<input placeholder="请填写容重" name="input" v-model="bulkDensity" @input="bulkDensityInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">焦糊粒(%){{'<='}}</text>
					<view class="con-list">
						<input placeholder="请填写焦糊粒" name="input" v-model="burnt" @input="burntInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">霉变粒(%){{'<='}}</text>
					<view class="con-list">
						<input placeholder="请填写霉变" name="input" v-model="moldy" @input="moldyInput"></input>
					</view>
				</view>
		</view>
		</view>
		<view class="cu-modal" :class="modalName=='inModal'?'show':''" @tap="hideModal">
			<scroll-view scroll-y class="cu-dialog" :style="userStyle" @tap.stop="">
				<view class="cu-list menu text-center" >
					<view class="cu-item" v-for="(item,index) in userList" :key="index" @click="selectInPerson(item)">
						<label class="flex justify-between align-center flex-sub">
							<view class="flex-sub">{{item.userName}}</view>
						</label>
					</view>
				</view>
			</scroll-view>
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
	import Handwriting from '@/components/ossutil/signature.js';
	import {  
	    mapState 
	} from 'vuex';
	export default {
		data() {
			return {
				purchaseInfo:[],
				poundImg:'',
				grossWeight:'',
				skinWeight:'',
				netWeight:'',
				taskStatus:'',
				userStyle:'',
				modalName:'',
				userList:[],
				inPersonName:'',
				inPersonId:'',
				taskType:'',
				ctx: [], //绘图图像
				points: [], //路径点集合 
				signature: '',
				taskId:'',
				waterContent:'',
				bulkDensity:'',
				burnt:'',
				moldy:'',
				goodsValue:'',
				addNumStatus:false,
				wechatNo:'',
				lineColor:'black',
				slideValue:50,
				handwriting:'',
				selectColor:'black',
				color:'',
			};
		},
		computed: {
			...mapState(['hasLogin','userInfo'])
		},
		onShow() {
		},
		onLoad(options) {
			const that = this
			this.taskStatus = options.taskStatus
			this.taskType = options.taskType
			this.taskId = options.taskId
			uni.showLoading({
				title: '正在加载',
				mask:true
			})
			that.$api.request('tran', 'getPurchaseInfoBySendCarNo', {
				purchaseNo: options.sendCarNo
			}, failres => {
				that.$api.msg(failres.errmsg)
				uni.hideLoading()
			}).then(res => {			
				that.purchaseInfo = res.data
				that.inPersonName = res.data.receiver
				that.grossWeight = that.purchaseInfo.grossWeightRec
				that.skinWeight = that.purchaseInfo.skinWeightRec
				that.netWeight = that.purchaseInfo.netWeightRec
				that.poundImg = that.purchaseInfo.poundImgRec
				that.waterContent = that.purchaseInfo.waterContentRec
				that.bulkDensity = that.purchaseInfo.bulkDensityRec
				that.burnt = that.purchaseInfo.burntRec
				that.moldy = that.purchaseInfo.moldyRec
				uni.hideLoading()
			})
		},
		onReady(){
			
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
			uploadScaleStart(event){
			  this.handwriting.uploadScaleStart(event)
		    },
		    uploadScaleMove(event){
			  this.handwriting.uploadScaleMove(event)
		    },
		    uploadScaleEnd(event){
			  this.handwriting.uploadScaleEnd(event)
		    },
			retDraw() {
				this.handwriting.retDraw()
			},
			selectInPerson(item){
				this.inPersonId = item.id
				this.inPersonName = item.userName
				this.modalName = null
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
			ViewSignImage(e) {
			  let imgsArray = [];
			  imgsArray[0] = e.currentTarget.dataset.url;
			  uni.previewImage({
				current: 0,
				urls: imgsArray,
			  });
			},
			DelImg(e) {
				uni.showModal({
					title: '提示',
					content: '确定要删除该磅单照片吗？',
					cancelText: '取消',
					confirmText: '确定',
					success: res => {
						if (res.confirm) {
							this.poundImg = "";
						}
					}
				})
			},
			ChooseImage() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'carNoImg/',
							result => {
								this.poundImg = result
								uni.hideLoading();
							}
						)
					}
				});
			},
			waterContentInput(e){
				this.waterContent = e.detail.value
			},
			bulkDensityInput(e){
				this.bulkDensity = e.detail.value
			},
			burntInput(e){
				this.burnt = e.detail.value
			},
			moldyInput(e){
				this.moldy = e.detail.value
			},
			grossWeightInput(e) {
				this.grossWeight = e.detail.value
				if(this.grossWeight && this.skinWeight){
					this.netWeight = this.numFilter(this.grossWeight - this.skinWeight)
					if(this.netWeight > this.purchaseInfo.num){
						this.addNumStatus = true
					}
				}
			},
			skinWeightInput(e) {
				this.skinWeight = e.detail.value
				if(this.grossWeight && this.skinWeight){
					this.netWeight = this.numFilter(this.grossWeight - this.skinWeight)
					if(this.netWeight > this.purchaseInfo.num){
						this.addNumStatus = true
					}
					else{
						this.addNumStatus = false
					}
				}
			},
			save(){
				const that = this
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				that.$api.request('tran', 'savePurchaseWaiInfo', {
					purchaseNo: that.purchaseInfo.sendCarNo,
					grossWeightRec:!that.grossWeight?'':that.grossWeight,
					skinWeightRec:!that.skinWeight?'':that.skinWeight,
					netWeightRec:!that.netWeight?'':that.netWeight,
					poundImgRec:!that.poundImg?'':that.poundImg,
					waterContentRec:!that.waterContent?'':that.waterContent,
					bulkDensityRec:!that.bulkDensity?'':that.bulkDensity,
					burntRec:!that.burnt?'':that.burnt,
					moldyRec:!that.moldy?'':that.moldy,
					taskType:that.taskType,
	
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
				
				if(!that.grossWeight){
					this.$api.msg('请填写毛重');
					return;
				}
				if(!that.skinWeight){
					this.$api.msg('请填写皮重');
					return;
				}
				if(!that.poundImg){
					this.$api.msg('请上传磅单照片');
					return;
				}
				if (!that.waterContent) {
					that.$api.msg('请填写水分');
					return
				}
				if (!that.bulkDensity) {
					that.$api.msg('请填写容重');
					return
				}
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				that.$api.request('tran', 'finishPurchaseWaiInfo', {
					purchaseNo: that.purchaseInfo.sendCarNo,
					grossWeightRec:that.grossWeight,
					skinWeightRec:that.skinWeight,
					netWeightRec:that.netWeight,
					poundImgRec:that.poundImg,
					waterContentRec:!that.waterContent?'':that.waterContent,
					bulkDensityRec:!that.bulkDensity?'':that.bulkDensity,
					burntRec:!that.burnt?'':that.burnt,
					moldyRec:!that.moldy?'':that.moldy,
					taskId:that.taskId
				}, failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				}).then(res => {			
					uni.hideLoading()
						that.$api.msg('完成成功')
						setTimeout(()=>{uni.navigateBack({
							delta: 1
						})},1000);
				})
			},
			selectPerson(){
				const that = this
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
						that.modalName = 'inModal'
					}
					
					uni.hideLoading()
				})
			},
			gaipai(){
				const that = this
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
						that.modalName = 'userModal'
					}
					
					uni.hideLoading()
				})
			},
			mygaipai(id){
				var that = this
				that.$api.request('tran', 'gaipai',{
					taskId: that.taskId,
					outPersonId:id,
					taskType:that.taskType
				},failres => {
					that.$api.msg(failres.errmsg)
					that.modalName = null
					uni.hideLoading()
				}).then(res => {		
					that.modalName = null
					setTimeout(()=>{uni.navigateBack({
						delta: 2
					})},1000);
					uni.hideLoading()
				})
			},
			
		},
	}
</script>

<style lang='scss' scoped="true">
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
				width: 33.3333%;
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
	.wrapper {
	  width: 100%;
	  height: 100%;
	  margin: 30upx 0;
	  overflow: hidden;
	  display: flex;
	  align-content: center;
	  flex-direction: column;
	  justify-content: center;
	  font-size: 28upx;
	}
	
	.handWriting {
	  background: #fff;
	  width: 100%;
	  height: 350upx;
	}
	
	.handRight {
	  align-items: center;
	}
	
	.handCenter {
	  border: 4upx dashed #e9e9e9;
	  flex: 5;
	  overflow: hidden;
	  box-sizing: border-box;
	  width: 90%;
	  margin: 0 auto;
	}
	.handTitle {
	  flex: 1;
	  color: #666;
	  justify-content: center;
	  font-size: 30upx;
	}
	.handBtn {
	  flex-direction: column;
	  padding: 40upx 20upx;
	}
	.buttons{
		width: 100%;
		margin-top: 20upx;
		justify-content: space-between;
	}
	.buttons>button{
		font-size: 30upx;
		height: 80upx;
	}
	.delBtn {
	  background: #23df02;
	  color: #fff;
	}
	.color{
		align-items: center;
	}
	.color>text{
		margin-right: 20upx;
	}
	.subBtn {
	  background: #008ef6;
	  color: #fff;
	  text-align: center;
	  justify-content: center;
	}
	
	.black-select {
	  width: 60upx;
	  height: 60upx;
	}
	.black-select.color_select {
	  width: 90upx;
	  height: 90upx;
	}
	.red-select {
	  width: 60upx;
	  height: 60upx;
	}
	.red-select.color_select {
	  width: 90upx;
	  height: 90upx;
	}
	.slide-wrapper {
	  align-items: center;
	  margin-bottom: 20upx;
	}
	.slider{
		width: 400upx;
		padding-left: 20upx;
	}
	.drop {
	  width: 50upx;
	  height: 50upx;
	  border-radius: 50%;
	  background: #FFF;
	  position: absolute;
	  left: 0upx;
	  top: -10upx;
	  box-shadow: 0px 1px 5px #888888;
	}
	
	.slide {
	  width: 250upx;
	  height: 30upx;
	}
	.showimg{
		border: 4upx solid #e9e9e9;
		overflow: hidden;
		width: 90%;
		margin: 0 auto;
		background: #eee;
		height: 350upx;
		margin-top: 40upx;
		align-items: center;
		justify-content: center;
	}
	.showimg>image{
		width: 100%;
		height: 100%;	
	}
	.showimg>text{
		font-size: 40upx;
		color: #888;
	}	
	.indexFixed{
		position: fixed;
		left:0;
		bottom:0;
		right:0;
	}
</style>
