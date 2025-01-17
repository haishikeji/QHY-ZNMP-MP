<template>
	<view :class="showAutograph?'indexFixed container':'container '" >
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
				<view class='cu-tag radius line-orange'>剩余量: {{numFilter(tranInfo.total - tranInfo.tranCount)}}吨</view>
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
				<view class="c-row b-b">
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
						<text>{{numFilter(tranInfo.total - tranInfo.tranCount)}}</text>
					</view>
				</view>
			</view>
			<view v-if='car.driver' class="d-header">
				<text>承运人：{{car.driver}}</text>
			</view>
			<view class="c-list">
				<view class="c-row b-b">
					<text class="tit">车牌号</text>
					<view class="con-list">{{car.carNo}}</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action">
						司机身份证(正、反面)照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="personNoImg != ''" @tap="ViewImage" :data-url="personNoImg">
						 <image :src="personNoImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="0">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="bg-img" v-if="personNoImg1 != ''" @tap="ViewImage" :data-url="personNoImg1">
						 <image :src="personNoImg1" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="1">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImagePerson" v-if="personNoImg == '' || personNoImg1 == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">承运人身份证号</text>
					<view class="con-list">
						<input placeholder="请填写" name="input" v-model="driverNo" @input="driverNoInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">承运人联系地址</text>
					<view class="con-list">
						<input placeholder="请填写" name="input" v-model="driverAddress" @input="driverAddressInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">承运人微信号</text>
					<view class="con-list">
						<input placeholder="请填写" type="mobile" name="input" v-model="wechatNo" @input="wechatNoInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">承运人电话</text>
					<view class="con-list">{{car.driverPhone}}</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">价格</text>
					<view class="con-list">{{car.price}}</view>
				</view>
				
				<view class="cu-bar bg-white ">
					<view class="action">
						司机驾驶证(主页、副页)照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="driverNoImg != ''" @tap="ViewImage" :data-url="driverNoImg">
						 <image :src="driverNoImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="2">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="bg-img" v-if="driverNoImg1 != ''" @tap="ViewImage" :data-url="driverNoImg1">
						 <image :src="driverNoImg1" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="3">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImageDriver" v-if="driverNoImg == '' || driverNoImg1 == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="cu-bar bg-white ">
					<view class="action">
						行车证(主页、副页)照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="carNoImg != ''" @tap="ViewImage" :data-url="carNoImg">
						 <image :src="carNoImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="4">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="bg-img" v-if="carNoImg1 != ''" @tap="ViewImage" :data-url="carNoImg1">
						 <image :src="carNoImg1" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="5">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImageCar" v-if="carNoImg == '' || carNoImg1 == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				
			</view> 
			
			<view class="d-header">
				<text>运费结算账户信息</text>
			</view>
			<view class="c-list">
				<view class="cu-bar bg-white">
					<view class="action">
						收款人身份证(正、反面)照片
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="recPersonImg != ''" @tap="ViewImage" :data-url="recPersonImg">
						 <image :src="recPersonImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="9">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="bg-img" v-if="recPersonImg1 != ''" @tap="ViewImage" :data-url="recPersonImg1">
						 <image :src="recPersonImg1" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="10">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImageRecPerson" v-if="recPersonImg == '' || recPersonImg1 == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">收款账户名称</text>
					<view class="con-list">
						<input placeholder="请填写" name="input" v-model="recPerson" @input="recPersonInput"></input>
					</view>
					<button class='cu-btn bg-green shadow' @click="getHistoryBank">选择银行卡</button>
				</view>
				<view class="c-row b-b">
					<text class="tit">收款人身份证号</text>
					<view class="con-list">
						<input placeholder="请填写" name="input" v-model="recPersonNo" @input="recPersonNoInput"></input>
					</view>
				</view>
				<view class="cu-bar bg-white">
					<view class="action">
						银行卡(正、反面)上传
					</view>
				</view>
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="bankImg != ''" @tap="ViewImage" :data-url="bankImg">
						 <image :src="bankImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="7">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="bg-img" v-if="bankImg1 != ''" @tap="ViewImage" :data-url="bankImg1">
						 <image :src="bankImg1" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="8">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseImageBank" v-if="bankImg == '' || bankImg1 == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">开户行</text>
					<view class="con-list">
						<input placeholder="请填写" name="input" v-model="bankAccount" @input="bankAccountInput"></input>
					</view>
				</view>
				<view v-if="bankPhone" class="c-row b-b">
					<text class="tit">开户行客服电话</text>
					<view class="con-list">
						{{bankPhone}}
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">开户支行</text>
					<view v-if="zhihangStatus" class="con-list">
						<picker @change="bankNameChange" :value="bankNameIndex" :range="bankNameList">
							<view class="picker">
								{{bankNameIndex>-1?bankNameList[bankNameIndex]:'点击选择支行'}}
							</view>
						</picker>
						
					</view>
					<view v-else class="con-list">
						<input placeholder="请填写" name="input" v-model="bankNameZhihang" @input="bankNameZhihangInput"></input>
					</view>
					<button v-if="zhihangStatus" class='cu-btn bg-green shadow' @click="changeZhihang">手动填写</button>
					<button v-else class='cu-btn bg-green shadow' @click="changeZhihang">自动识别</button>
				</view>
				<view class="c-row b-b">
					<text class="tit">银行卡号</text>
					<view class="con-list">
						<input placeholder="请填写" name="input" v-model="bankNo" @input="bankNoInput"></input>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit">合同内容</text>
					<view class="con-list">
						<button class='cu-btn bg-green shadow' @click="downLoadContract" >查看</button>
					</view>
				</view>
				<view class="cu-bar bg-white ">
					<view class="action">
						我的签名
					</view>
				</view>
				<!-- <button class='cu-btn bg-green shadow' @click="showGraph">手写签名</button> -->
				<view class="cu-form-group">
					<view class="grid col-2 grid-square flex-sub">
						<view class="bg-img" v-if="signImg != ''" @tap="ViewImageSign" :data-url="signImg">
						 <image :src="signImg" mode="aspectFit"></image>
							<view class="cu-tag bg-red" @tap.stop="DelImg" :data-index="6">
								<text class='cuIcon-close'></text>
							</view>
						</view>
						<view class="solids" @tap="ChooseSignImage" v-if="signImg == ''">
							<text class='cuIcon-cameraadd'></text>
						</view>
					</view>
				</view>
			</view>
			
		</view>
		<view class="cu-modal" :class="modalName=='MyBankModal'?'show':''" @tap="hideModal">
			<scroll-view scroll-y class="cu-dialog" :style="myBankStyle" @tap.stop="">
				<view class="cu-list menu text-center" >
					<view class="cu-item" v-for="(item,index) in bankInfo" :key="index" @click="myBankClick(item)">
						<label class="flex justify-between align-center flex-sub">
							<view class="flex-sub">{{item.secondBank+" ****"+item.bankNo4+" "+item.accountName}}</view>
						</label>
					</view>
				</view>
			</scroll-view>
		</view>
		<view class="wrapper" v-if="showAutograph">
			<view class="handCenter">
				<canvas class="handWriting" disable-scroll="true" @touchstart="uploadScaleStart" @touchmove="uploadScaleMove"
				  @touchend="uploadScaleEnd" @tap="mouseDown" canvas-id="handWriting">
				</canvas>
		    </view>
			<view class="buttons">
				<button @click="retDraw" class="delBtn">重写</button>
				<button @click="subCanvas" class="subBtn">保存</button>	
			</view>
		</view>
		<view v-if="car.status == 25" class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="wanshan">签订运输合同</button>
			</view>
		</view>
	
		<view class="cu-modal" :class="modalName=='RadioModal'?'show':''" @tap="hideModal">
			<scroll-view scroll-y class="cu-dialog" style="height: 800rpx;" @tap.stop="">
				<radio-group class="block" @change="RadioChange">
					<view class="cu-list menu text-left">
						<view class="cu-item" v-for="(item,index) in tranInfo" :key="index">
							<label class="flex justify-between align-center flex-sub">
								<view class="flex-sub">{{item.tranNo}}(剩余量：{{item.total-item.tranCount}})</view>
								
								<radio class="round" :class="tranNo==item.tranNo?'checked':''" :checked="tranNo==item.tranNo?true:false"
								  :value="index"></radio>
							</label>
						</view>
					</view>
				</radio-group>
			</scroll-view>
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
				car: {
					id:'',
					tranNo:'',
					tranCount: 0,
					price:0,
					driverPhone:'',
				},
				bankInfo:[],
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
				bankImg:'',
				bankImg1:'',
				signImg:'',
				showAutograph: false, //签名弹框是否显示
				ctx: [], //绘图图像
				points: [], //路径点集合 
				signature: '',
				bankAccount:'',
				bankNo:'',
				bankNameZhihang:'',
				bankNameList:'',
				bankPhone:'',
				bankNameIndex:-1,
				zhihangStatus:true,
				recPersonImg:'',
				recPersonImg1:'',
				recPerson:'',
				recPersonNo:'',
				wechatNo:'',
				driverNo:'',
				driverAddress:'',
				myBankStyle:'',
				bankNo4:'',
				lineColor:'black',
				slideValue:50,
				handwriting:'',
				selectColor:'black',
				color:'',
			};
		},
		onShow() {
		},
		onLoad(options) {
			const that = this
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
				that.bankNo = res.data.bankNo
				that.bankAccount = res.data.bankAccount
				that.signImg = res.data.signImg
				that.recPersonImg = res.data.recPersonImg
				that.recPersonImg1 = res.data.recPersonImg1
				that.driverNo = res.data.driverNo
				that.driverAddress = res.data.driverAddress
				that.wechatNo = res.data.wechatNo
				uni.hideLoading()
			})
		},
		computed: {
			...mapState(['hasLogin','userInfo'])
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
			ChooseSignImage() {
				this.handwriting = new Handwriting({
				  lineColor: this.lineColor,
				  slideValue: this.slideValue, // 0, 25, 50, 75, 100
				  canvasName: 'handWriting',
				})
				this.showAutograph = true
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
			subCanvas(){
			  this.handwriting.saveCanvas().then(res=>{
				let that = this;
				uploadImage(res, 'signImg/',
					result => {
						that.signImg = result
						that.showAutograph = false
						uni.hideLoading();
					}
				)
			  }).catch(err=>{
				  console.log(err);
			  });		  
			  
			},
			myBankClick(item){
				this.modalName = null
				this.bankNo = item.secondAccountNo
				this.bankAccount = item.secondBank
				this.bankNameZhihang = item.secondBankBranch
				this.recPerson = item.accountName
				this.recPersonNo = item.recPersonNo
				this.recPersonImg = item.recPersonImg
				this.recPersonImg1 = item.recPersonImg1
				this.bankNo4 = item.bankNo4
				this.bankImg = item.bankImg
				this.bankImg1 = item.bankImg1
				this.zhihangStatus = false
			},
			hideModal(e) {
				this.modalName = null
			},
			downLoadContract(){
				var that = this
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				uni.downloadFile({
				  url: that.car.tranContractPdf,
				  success: function (res) {
				    var filePath = res.tempFilePath;
				    uni.openDocument({
				      filePath: filePath,
				      success: function (res) {
						  uni.hideLoading()
				      }
				    });
				  },
				})
			},
			changeZhihang(){
				this.zhihangStatus = !this.zhihangStatus
			},
			bankNameChange(e) {
				this.bankNameIndex = e.detail.value
				this.bankNameZhihang = this.bankNameList[this.bankNameIndex]
			},
			driverAddressInput(e) {
				this.driverAddress = e.detail.value
			},
			driverNoInput(e) {
				this.driverNo = e.detail.value
			},
			wechatNoInput(e) {
				this.wechatNo = e.detail.value
			},
			bankNoInput(e) {
				this.bankNo = e.detail.value
			},
			recPersonNoInput(e) {
				this.recPersonNo = e.detail.value
			},
			recPersonInput(e) {
				this.recPerson = e.detail.value
			},
			bankAccountInput(e) {
				this.bankAccount = e.detail.value
			},
			bankNameZhihangInput(e) {
				this.bankNameZhihang = e.detail.value
			},
			getHistoryBank(){
				const that = this
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				that.$api.request('tran', 'getHistoryBank', failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				}).then(res => {
					that.bankInfo = res.data
					if(that.bankInfo.length == 0){
						that.$api.msg('暂无历史银行卡');
						uni.hideLoading()
						return
					}
					var height = that.bankInfo.length * 100
					var width = 500
					that.myBankStyle = "height:"+height+"rpx;width:" + width+"rpx"
					that.modalName = 'MyBankModal'
					uni.hideLoading()
				})
				
			},
			wanshan(){
				const that = this
				if (!that.personNoImg || !that.personNoImg1) {
					that.$api.msg('请上传身份证正、反面照片(2张)');
					return
				}
				if (!that.driverNoImg || !that.driverNoImg1) {
					that.$api.msg('请上传驾驶证主、副页照片(2张)');
					return
				}
				if (!that.carNoImg || !that.carNoImg1) {
					that.$api.msg('请上传行车证主、副页照片(2张)');
					return
				}
				if (!that.bankImg || !that.bankImg1) {
					that.$api.msg('请上传收款人银行卡正、反面照片(2张)');
					return
				}
				if (!that.recPersonImg || !that.recPersonImg1) {
					that.$api.msg('请上传收款人身份证正、反面照片(2张)');
					return
				}
				if (!that.signImg) {
					that.$api.msg('请手写签名');
					return
				}
				if (!that.bankAccount) {
					that.$api.msg('请填写开户行');
					return
				}
				if (!that.bankNameZhihang) {
					that.$api.msg('请填写开户支行');
					return
				}
				if (!that.bankNo) {
					that.$api.msg('请填写银行卡号');
					return
				}
				if (!that.recPerson) {
					that.$api.msg('请填写收款账户名称');
					return
				}
				if (!that.recPersonNo) {
					that.$api.msg('请填写收款人身份证号');
					return
				}
				if (!that.driverNo) {
					that.$api.msg('请填写承运人身份证号');
					return
				}
				if (!that.driverAddress) {
					that.$api.msg('请填写承运人联系地址');
					return
				}
				if (!that.wechatNo) {
					that.$api.msg('请填写承运人微信号');
					return
				}
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				that.$api.request('tran', 'wanshan', {
					id: that.car.id,
					personNoImg:that.personNoImg,
					personNoImg1:that.personNoImg1,
					driverNoImg:that.driverNoImg,
					driverNoImg1:that.driverNoImg1,
					carNoImg:that.carNoImg,
					carNoImg1:that.carNoImg1,
					bankImg:that.bankImg,
					bankImg1:that.bankImg1,
					bankNo:that.bankNo,
					bankAccount:that.bankAccount,
					signImg:that.signImg,
					bankNameZhihang:that.bankNameZhihang,
					recPerson:that.recPerson,
					recPersonNo:that.recPersonNo,
					recPersonImg:that.recPersonImg,
					recPersonImg1:that.recPersonImg1,
					driverNo:that.driverNo,
					driverAddress:that.driverAddress,
					wechatNo:that.wechatNo
				}, failres => {
					that.$api.msg(failres.errmsg)
					uni.hideLoading()
				}).then(res => {			
					that.car.status = 13
					that.$api.msg('签订成功')
					uni.hideLoading()
				})
			},
			ChooseImageBank() {
				var that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						uploadImage(res.tempFilePaths[0], 'bankImg/',
							result => {
							if(that.bankImg == undefined){
								that.bankImg = ''
							}
							 if (that.bankImg.length != 0) {
							 	that.bankImg1 = result
								uni.hideLoading()
							 } else {
							 	that.bankImg = result
								that.$api.request('tran', 'bankShibie', {
									bankImg: result
								}, failres => {
									that.$api.msg(failres.errmsg)
									uni.hideLoading()
								}).then(res => {			
									that.bankAccount = res.data.bankName
									that.bankNo = res.data.bankNo
									that.bankNameList = res.data.bankNameZhihang
									that.bankPhone = res.data.bankPhone
									that.$api.msg('请核对开户行、支行和银行卡号')
									uni.hideLoading()
								})
							 }
							}
						)
					}
				});
			},
			ChooseImagePerson() {
				const that = this
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
							 	uni.hideLoading()
							 } else {
							 	this.personNoImg = result
								that.$api.request('tran', 'personShibie', {
									personImg: result,
									type:"driver"
								}, failres => {
									that.$api.msg(failres.errmsg)
									uni.hideLoading()
								}).then(res => {			
									that.driver = res.data.driver
									that.driverNo = res.data.driverNo
									that.driverAddress = res.data.driverAddress
									that.$api.msg('请核对身份信息')
									uni.hideLoading()
								})
							 }
							}
						)
					}
				});
			},
			ChooseImageRecPerson() {
				var that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						uploadImage(res.tempFilePaths[0], 'recPersonImg/',
							result => {
							if(that.recPersonImg == undefined){
								that.recPersonImg = ''
							}
							 if (that.recPersonImg.length != 0) {
							 	that.recPersonImg1 = result
								uni.hideLoading()
							 } else {
							 	that.recPersonImg = result
								that.$api.request('tran', 'personShibie', {
									personImg: result,
									type:"recPerson"
								}, failres => {
									that.$api.msg(failres.errmsg)
									uni.hideLoading()
								}).then(res => {			
									that.recPerson = res.data.recPerson
									that.recPersonNo = res.data.recPersonNo
									that.$api.msg('请核对身份信息')
									uni.hideLoading()
								})
							 }
							}
						)
					}
				});
			},
			ChooseImageDriver() {
				var that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'driverNoImg/',
							result => {
								if(that.driverNoImg == undefined){
									that.driverNoImg = ''
								}
								if (that.driverNoImg.length != 0) {
									that.driverNoImg1 = result
								} else {
									that.driverNoImg = result
								}
								uni.hideLoading();
							}
						)
					}
				});
			},
			ChooseImageCar() {
				var that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: (res) => {
						//上传图片
						//图片路径可自行修改
						uploadImage(res.tempFilePaths[0], 'carNoImg/',
							result => {
								if(that.carNoImg == undefined){
									that.carNoImg = ''
								}
								if (that.carNoImg.length != 0) {
									that.carNoImg1 = result
								} else {
									that.carNoImg = result
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
			ViewImageSign(e) {
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
							else if(e.currentTarget.dataset.index == 6){
								this.signImg = "";
							}
							else if(e.currentTarget.dataset.index == 7){
								this.bankImg = "";
							}
							else if(e.currentTarget.dataset.index == 8){
								this.bankImg1 = "";
							}
							else if(e.currentTarget.dataset.index == 9){
								this.recPersonImg = "";
							}
							else if(e.currentTarget.dataset.index == 10){
								this.recPersonImg1 = "";
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
				width: 100%;
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

	button::after {
		border: none;
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
