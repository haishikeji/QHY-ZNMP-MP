<template>
	<view class="content">
		<view class='wrap'>
			<u-navbar title="电子名片" placeholder bgColor="#112253" titleStyle="color:#fff">
				<view class="u-nav-slot relative" slot="left">
					<u-icon name="bell" size="26" @click="toNotice" color="#fff"></u-icon>
					<u-badge :isDot="unread>0?true:false" type="error" class="point"></u-badge>
				</view>
			</u-navbar>
			<view class="content1 flex-row-center">
				<view class="search flex justify-space-between align-item-center flex-between" @click="search">
					<view class="left flex">
						<uni-icons type="search" size="24" color="#9199af"></uni-icons>
						<text class="search-val"> {{searchVal?searchVal:'搜索名片'}}</text>
					</view>

					<view class="right">
						<image style='width:32rpx;height:32rpx;' v-if="searchVal" @click.native.stop="delSearchVal"
							src="../../static/imgs/card/searchdel.png" mode="widthFix"></image>
						<uni-icons type="mic" size="24" @click.native.stop="micOpen" color="#9199af"
							style="margin-left: 20rpx;"></uni-icons>
					</view>
				</view>
				<view class="col flex">
					<uni-icons type="scan" size="30" @click="scan" color="#fff"></uni-icons>
				</view>
			</view>

			<view
				:style='cardHolderList.length>0?"padding:20rpx 20rpx 200rpx 20rpx;":"padding:20rpx 20rpx 20rpx 20rpx;"'
				class="content2" v-if="cardHolderList.length>0">
				<view class="all-type flex" @click="selectType">
					<text style="margin-right: 10rpx;">{{typeName?typeName:'全部分类'}}</text>
					<uni-icons type="bottom" size="18" color="#fff"></uni-icons>
				</view>
			</view>
		</view>
		<!-- :style='cardHolderList.length>0?"top:376rpx;":"top:276rpx;"' -->
		<view class="content3 relative" style="top: 380rpx;">

			<mescroll-uni v-if='cardHolderList' :up="upOption" :down="downOption" ref="mescrollRef" @init="mescrollInit"
				@up="upCallback" @down="downCallback" height="1060">
				<view v-for='(item,index) in cardHolderList' @longpress="longpress" class="item flex">
					<!-- <view class="card-list-item" style="background:red"> -->
					<view class="card-list-item"
						:style="'background: url('+item.currentBackground+');background-size:106% 111%;background-position: -20rpx -23rpx'">
						<view class="card-content style1" v-if="item.cuttentTemplate==1">
							<view class="left">
								<u--image :showLoading="true" :src="item.headSculpture" width="66px" height="66px"
									shape="circle"></u--image>
							</view>
							<view class="right">
								<view class="row1">
									<text class="name">{{item.name}}</text>
									<text class="post">{{item.post}}</text>
								</view>
								<view class="row2">
									{{item.companyName}}
								</view>
								<view class="row3 flex" @click="toMap(item)">
									<u--image :showLoading="true" :src="'/static/imgs/card/address'+item.icon+'.png'"
										width="13px" height="16px"></u--image>
									<view class='icon-text'>{{ item.province }}{{ item.city }}{{ item.area }}</view>
								</view>
								<view @click="freeTell(item)" class="row4 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.icon+'.png'"
										width="12px" height="13px"></u--image>
									<view class='icon-text'>{{ item.phone }}</view>
								</view>
								<view @click='remarkEdit(item)' class="row2 flex" style="color: #808080">
									<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.icon+'.png'"
										width="10px" height="12px"></u--image>
									<view class='icon-text' style="color: #808080;">{{item.remark?item.remark:'单击添加备注'}}
									</view>
								</view>
							</view>
							<u-checkbox-group v-if='islongPress' placement="column"
								@change="checkboxChange($event,index)">
								<u-checkbox iconColor='#4977FC' activeColor="#fff" :name='index+1'
									:checked='item.checked' :customStyle="{marginBottom: '8px'}">
								</u-checkbox>
							</u-checkbox-group>
						</view>
						<view class="card-content style1 flex-between" v-if="item.cuttentTemplate==2">
							<view class="right">
								<view class="row1">
									<text class="name">{{item.name}}</text>
									<text class="post">{{item.post}}</text>
								</view>
								<view class="row2">
									{{item.companyName}}
								</view>
								<view @click="toMap(item)" class="row3 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/address'+item.icon+'.png'"
										width="13px" height="16px"></u--image>
									<view class='icon-text'>{{ item.province }}{{ item.city }}{{ item.area }}</view>
								</view>
								<view @click="freeTell(item)" class="row4 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.icon+'.png'"
										width="12px" height="13px"></u--image>
									<view class='icon-text'>{{ item.phone }}</view>
								</view>
								<view @click='remarkEdit(item)' class="row2 flex" style="color: #808080">
									<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.icon+'.png'"
										width="10px" height="12px"></u--image>
									<view class='icon-text' style="color: #808080;">{{item.remark?item.remark:'单击添加备注'}}
									</view>
								</view>
							</view>
							<view class="left">
								<u--image :showLoading="true" :src="item.headSculpture" width="66px" height="66px"
									shape="circle" v-if="item.headSculpture"></u--image>
								<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px"
									height="66px" shape="circle" v-else></u--image>
							</view>
							<u-checkbox-group v-if='islongPress' placement="column"
								@change="checkboxChange($event,index)">
								<u-checkbox iconColor='#4977FC' activeColor="#fff" :name='index+1'
									:checked='item.checked' :customStyle="{marginBottom: '8px'}">
								</u-checkbox>
							</u-checkbox-group>
						</view>
						<view class="card-content style2" v-if="item.cuttentTemplate==3">
							<view class="top">
								<u--image :showLoading="true" :src="item.headSculpture" width="66px" height="66px"
									shape="circle" v-if="item.headSculpture"></u--image>
								<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px"
									height="66px" shape="circle" v-else></u--image>
							</view>
							<view class="bottom flex">
								<view class="left">
									<view class="row1">
										<text class="name">{{item.name}}</text>
										<text class="post">{{item.post}}</text>
									</view>
									<view class="row2">
										{{item.companyName}}
									</view>
								</view>
								<view class="right">
									<view @click="toMap(item)" class="row1 flex">
										<u--image :showLoading="true"
											:src="'/static/imgs/card/address'+item.icon+'.png'" width="13px"
											height="16px"></u--image>
										<view class='icon-text'>{{ item.province }}{{ item.city }}{{ item.area }}</view>
									</view>
									<view @click="freeTell(item)" class="row2 flex">
										<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.icon+'.png'"
											width="12px" height="13px"></u--image>
										<view class='icon-text'>{{ item.phone }}</view>
									</view>
									<view @click='remarkEdit(item)' class="row2 flex" style="color: #808080">
										<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.icon+'.png'"
											width="10px" height="12px"></u--image>
										<view class='icon-text' style="color: #808080;">
											{{item.remark?item.remark:'单击添加备注'}}
										</view>
									</view>
								</view>
							</view>
							<u-checkbox-group v-if='islongPress' placement="column"
								@change="checkboxChange($event,index)">
								<u-checkbox iconColor='#4977FC' activeColor="#fff" :name='index+1'
									:checked='item.checked' :customStyle="{marginBottom: '8px'}">
								</u-checkbox>
							</u-checkbox-group>
						</view>
						<view class="card-content style2" v-if="item.cuttentTemplate==4">
							<view class="top">
								<view class="row1">
									<text class="name">{{item.name}}</text>
									<text class="post">{{item.post}}</text>
								</view>
								<view class="row2">
									{{item.companyName}}
								</view>

							</view>
							<view class="bottom flex">
								<view class="left">
									<u--image :showLoading="true" :src="item.headSculpture" width="66px" height="66px"
										shape="circle" v-if="item.headSculpture"></u--image>
									<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px"
										height="66px" shape="circle" v-else></u--image>
								</view>
								<view class="right">
									<view @click="toMap(item)" class="row1 flex">
										<u--image :showLoading="true"
											:src="'/static/imgs/card/address'+item.icon+'.png'" width="13px"
											height="16px"></u--image>
										<view class='icon-text'>{{ item.province }}{{ item.city }}{{ item.area }}</view>
									</view>
									<view @click="freeTell(item)" class="row2 flex">
										<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.icon+'.png'"
											width="12px" height="13px"></u--image>
										<view class='icon-text'>{{ item.phone }}</view>
									</view>
									<view @click='remarkEdit(item)' class="row2 flex" style="color: #808080">
										<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.icon+'.png'"
											width="10px" height="12px"></u--image>
										<view :style='item.remark?"color:#000;":"color:#808080;"' class='icon-text'>
											{{item.remark?item.remark:'单击添加备注'}}
										</view>
									</view>
								</view>
							</view>
							<u-checkbox-group v-if='islongPress' placement="column"
								@change="checkboxChange($event,index)">
								<u-checkbox iconColor='#4977FC' activeColor="#fff" :name='index+1'
									:checked='item.checked' :customStyle="{marginBottom: '8px'}">
								</u-checkbox>
							</u-checkbox-group>
						</view>
						<view class="car-bottom flex">
							<view class="left">
								<image src="../../static/imgs/card/home.png" mode="widthFix"
									style="width: 32rpx;height: auto;" @click="toHome(item)"></image>
								<text @click='switchType(item)'
									style="color: #fff;font-size: 36rpx;font-weight: 700;">{{item.classify?item.classifyName:'默认'}}</text>
								<image v-if='item.shareCard==1' src="../../static/imgs/card/share.png" mode="widthFix"
									style="width: 35rpx;height: auto;" @click="share(item)"></image>

								<!-- <image v-if='item.shareCard==0' src="../../static/imgs/card/share_grey.png"
									mode="widthFix" style="width: 40rpx;height: auto;"></image> -->
							</view>
							<view class="right">

							</view>
						</view>
					</view>
				</view>
			</mescroll-uni>
		</view>
		<view class="del-style" v-if="islongPress">
			<view class="left">
				<text class="qx" @click="islongPress=false">取消</text>
				<text class="del" @click="del">删除</text>
			</view>
			<view style='width:128rpx;' class="right flex">
				<view>全选</view>
				<u-checkbox-group class='' placement="column" @change="checkboxChange($event,'')">
					<u-checkbox iconColor='#4977FC' activeColor="#fff" name='全选' :checked='checked'
						:customStyle="{marginBottom: '8px',position: 'relative',top: '-19px',right:'-8px'}">
					</u-checkbox>
				</u-checkbox-group>
			</view>

		</view>
		<u-picker :immediateChange="true" :show="isShowType" :columns="typeColumns" keyName="circleName"
			:title="type == 2?'修改分类':'选择分类'" @close="isShowType=false" @cancel="isShowType=false" closeOnClickOverlay
			@confirm="typeConfirm"></u-picker>
		<u-toast ref="uToast"></u-toast>
		<u-modal :show="delShow" title="提示" confirmText='确定' showCancelButton='true' @cancel="delShow=false"
			@confirm="delConfirm" :content='"已选中"+this.checkedList.length+"张名片，确定删除？"'></u-modal>
		<u-modal :show="modalShow" @confirm='remarkConfirm' :confirmColor="remark?'#2979ff':'#ccc'"
			showCancelButton='true' title="备注" @cancel="modalShow=false">
			<view class="slot-content">
				<u--textarea v-model="remark" placeholder="输入备注，不超过150个字"></u--textarea>
			</view>
		</u-modal>

		<u-popup :show="qrcodeShow" @close="qrcodeShow=false" mode="center" :round='10'>
			<image style='margin:10px;height: auto;' :src="currectData.qrCode" mode="widthFix"></image>
		</u-popup>
		<u-popup :show="popupshow" :closeOnClickOverlay='true' :overlayOpacity='0.4' @close='handleHiddenShare'
			:round="10" mode="bottom">
			<view>
				<!-- <view class="share-to">
					<text>分享到</text>
				</view> -->
				<view class="share-content">

					<view class="block" @click="toUrl()">
						<button class="moment">
							<image style='width:108rpx;height:108rpx;' src="@/static/imgs/scancode.png"
								mode="aspectFill"></image>
							<view>扫码分享</view>
						</button>
					</view>
					<view class="block">
						<button class="shareBtn" type="default" data-name="shareBtn" open-type="share">
							<image style='width:108rpx;height:108rpx;' src="@/static/imgs/shade.png" mode="aspectFill">
							</image>
							<view>分享微信好友</view>
						</button>
					</view>
				</view>
			</view>
		</u-popup>
		<!-- <image :src="poster" style="width: 750rpx;height: 1334rpx;"></image> -->
		<!-- 生成图片 -->
		<poster :data="canvasData" background-color="#FFF" :width='750' :height='420' @on-success="posterSuccess"
			ref="poster" @on-error="posterError"></poster>
		<!-- #ifdef MP-WEIXIN -->
		<u-modal :show="showAuthorizePhone" :showConfirmButton="false">
			<view class="slot-content">
				<view class="auth-card">
					<view class="img">
						<img class="avatar-img" src="@/static/imgs/logo.png" mode="widthFix">
					</view>
					<view class="content">手机登录后才能查看名片哦~</view>
				</view>
				<view class="auth-btncard">
					<view class="btn-unok">
						<u-button :customStyle="customStyleUnOk" @click="showAuthorizePhone=false" :plain="true">
							拒绝</u-button>
					</view>
					<view class="btn-ok">
						<u-button :customStyle="customStyleOk" open-type="getPhoneNumber"
							@getphonenumber="getPhoneNumber"> 立即登录</u-button>
					</view>
				</view>
			</view>
		</u-modal>
		<u-modal :show="showAuthorizeUser" :showConfirmButton="false">
			<view class="slot-content">
				<view class="auth-card">
					<view class="img">
						<img class="avatar-img" src="/static/imgs/logo.png" mode="widthFix">
					</view>
					<view class="content">邀请您补全个人信息<br></br>（昵称、头像）</view>
					<view style="margin-left: 100rpx;margin-right: 100rpx">
						<u-form :model="userInfo" ref="uForm">
							<u-form-item label="头像">
								<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar"
									slot="right">
									<image class="avatar"
										:src="userInfo.head?userInfo.head:'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'">
									</image>
								</button>
							</u-form-item>
							<u-form-item label="昵称">
								<u-input inputAlign='right' v-model="userInfo.nickname" class="weui-input"
									@blur="userNameInput" placeholder="请输入昵称" border="false" />
								<!-- <input type="nickname" :value="userInfo.nickname" class="weui-input" @blur="userNameInput" placeholder="请输入昵称"/> -->
							</u-form-item>
						</u-form>
					</view>
				</view>
				<view class="auth-btncard">
					<view class="btn-unok"><u-button :customStyle="customStyleUnOk" @click="authUser(0)">
							拒绝</u-button>
					</view>
					<view class="btn-ok">
						<u-button :customStyle="customStyleOk" @click="authUser(1)"> 允许</u-button>
					</view>
				</view>
			</view>
		</u-modal>

		<!-- #endif -->
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	var that;
	import Poster from '../../components/zhangyuhao-poster/Poster.vue'
	import {
		pathToBase64,
		base64ToPath
	} from 'image-tools'
	import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
	export default {
		mixins: [MescrollMixin],
		components: {
			Poster
		},
		data() {
			return {
				userInfo: {
					head: '',
					nickname: '',
					phone: '',
				},
				downOption: {
					auto: false,
					textColor: '#bbb'
				},
				upOption: {
					page: {
						size: 10 // 每页数据的数量,默认10
					},
					auto: false,
					noMoreSize: 1,
					textNoMore: '没有更多了~',
					textColor: '#bbb'
				},
				params: {
					encryptedData: '',
					session_key: '',
					iv: '',
				},
				key: '',
				isPhone: false,
				canReset: false,
				poster: '',
				canvasData: {},
				searchVal: '',
				isShowType: false,
				loading: true,
				typeName: '',
				typeColumns: [],
				cardHolderList: [],
				currectData: [],
				type: 1,
				popupshow: false,
				modalShow: false,
				remark: '',
				islongPress: false,
				checked: false,
				checkedList: [],
				delShow: false,
				qrcodeShow: false,
				unread: 0,
				clicknum: 0,
				noticeList: [],
				status: 0,
				// 是否弹出登录注册授权弹窗
				showAuthorizeUser: false,
				showAuthorizePhone: false,
				customStyleUnOk: {
					marginTop: '20rpx',
					color: '#18254C',
					border: '2px solid #18254C',
					"border-radius": "10px",
					fontSize: "32rpx"
				},
				customStyleOk: {
					marginTop: '20rpx',
					color: '#fff',
					border: '2px solid #18254C',
					"border-radius": "10px",
					fontSize: "32rpx",
					background: "#18254C"
				},
			};
		},
		mounted() {

		},
		onShow: function() {
			let _isLoad = uni.getStorageSync("scanAddCardToIndex")
			this.searchVal = uni.getStorageSync("search_val") ? uni.getStorageSync("search_val") : ''
			if (this.searchVal || _isLoad) this.mescroll.resetUpScroll();
			if (uni.getStorageSync("userInfo").phone) {
				that.$request.baseRequest('admin.unimall.cardNewsInfo', 'list', {
					receiveId: uni.getStorageSync("userInfo").id,
				}, failres => {
					console.log('res+++++', failres.errmsg)
					that.$refs.uToast.show({
						type: 'error',
						message: failres.errmsg,
					})
				}).then(res => {
					that.noticeList = res.data.items
					if (that.noticeList.length > 0) {
						var data = that.noticeList.filter((item) => {
							return item.newsFlag == 0
						})
						that.unread = data ? data.length : 0
					}
					console.log(that.unread)
				})
			} else {
				this.showAuthorizePhone = true
			}

		},
		onLoad: function(options) {
			that = this
			if (uni.getStorageSync("userInfo").phone) {
				this.userInfo = uni.getStorageSync("userInfo")
				this.$nextTick(function() {
					that.mescroll.resetUpScroll()
					wx.showShareMenu({
						withShareTicket: true,
						menus: ["shareAppMessage", "shareTimeline"]
					})
				});

			}
			this.status = uni.getLaunchOptionsSync().scene
			if (uni.getLaunchOptionsSync().scene == 1154) {
				uni.showToast({
					title: '请前往小程序查看名片',
					icon: 'none',
					duration: 10000
				});
			}

		},
		onShareAppMessage(res) {
			console.log(res, this.popupshow)
			let that = this;
			//生成名片图片
			let imageUrl = this.poster
			console.log("imageUrl", imageUrl)
			if (res.from === 'button') {
				let path = `/pageA/cardHolder/scanCodeAddCard?id=${that.currectData.id}`
				return {
					title: this.popupshow ? `${that.currectData.name}分享的名片~` : '电子名片',
					path: path,
					imageUrl: this.popupshow ? imageUrl : '',
				};
			}
			if (res.from === 'menu') {
				return {
					title: '名片夹',
					path: '/pages/tabBarPro/index/index',
					imageUrl: this.popupshow ? imageUrl : ''
				};
			}
		},
		methods: {
			//获取昵称输入内容
			userNameInput(e) {
				this.userInfo.nickname = e.detail.value
			},
			async onChooseAvatar(e) {
				this.$set(this.userInfo, "head", await this.toBase64(e.detail.avatarUrl))
			},
			toBase64(url) {
				return new Promise(resolve => {
					pathToBase64(url).then(path => {
						resolve(path);
					}).catch(error => {
						console.log(error)
					})
				})
			},
			async authUser(type) {
				//同步信息，没有头像和昵称自动生成
				this.userInfo = await this.$request.syncInfo(this.userInfo)
				if (this.userInfo.openId) {
					uni.setStorageSync("userInfo", that.userInfo)
					that.showAuthorizeUser = false
					that.mescroll.resetUpScroll()
				}
			},

			downCallback() {
				if (uni.getStorageSync("userInfo").phone) {
					this.mescroll.resetUpScroll()
				} else {
					that.mescroll.endBySize(0, 0)
					this.showAuthorizePhone = true
				}

			},
			freeTell(item) {
				uni.makePhoneCall({
					phoneNumber: item.phone, //仅为示例，并非真实的电话号码
					success: function() {
						console.log("拨打电话成功！")
					},
					fail: function() {
						console.log("拨打电话失败！")
					}
				})
			},
			async getPhoneNumber(e) {
				that.userInfo = await this.$request.wxlogin()
				that.userInfo.phone = await this.$request.getPhone(e, that.userInfo)
				this.showAuthorizePhone = false
				this.showAuthorizeUser = true

			},
			delVal() {
				this.searchVal = ""
				this.mescroll.resetUpScroll()
			},
			handleHiddenShare() {
				console.log(11111)
				this.popupshow = false
			},
			posterError(err) {
				console.log(err)
				uni.hideLoading()
			},
			posterSuccess(url) {
				console.log("hahahah", url)
				// 生成成功，会把临时路径在这里返回
				this.poster = url;
				this.popupshow = true
				console.log(url)
				uni.hideLoading()
			},
			toHome(item) {
				uni.navigateTo({
					url: "/pageA/circle/lookHome?id=" + item.personalHomeId
				})
			},
			delSearchVal() {
				this.searchVal = ""
				uni.removeStorageSync('search_val')
				this.mescroll.resetUpScroll()
			},
			toUrl() {
				console.log(this.currectData)
				this.popupshow = false
				this.qrcodeShow = true
			},
			share(item) {
				uni.showLoading({
					title: '加载中',
					mask: true
				})
				this.currectData = item
				var data = [{
						type: 'image',
						path: this.currectData.currentBackground,
						use: 'bg',
						x: -10,
						y: 0,
						width: 530,
						height: 325
					},
					{
						type: 'image',
						path: this.currectData.headSculpture ? this.currectData.headSculpture :
							'../../static/imgs/card/defaulthead.png',
						shape: 'circle',
						use: 'head',
						imageType: this.currectData.headSculpture ? 'wl' : 'bd',
						x: 30,
						y: 30,
						width: 100,
						height: 100
					},
					{
						type: 'text',
						text: this.currectData.name,
						use: 'name',
						x: 150,
						y: 50,
						size: 24,
						color: '#000'

					},
					{
						type: 'text',
						text: this.currectData.post,
						use: 'post',
						x: 240,
						y: 55,
						size: 18,
						color: '#666666'

					},
					{
						type: 'text',
						text: this.currectData.companyName,
						use: 'companyName',
						x: 150,
						y: 95,
						size: 18,
						color: '#000'

					},
					{
						type: 'image',
						path: '../../static/imgs/card/address1.png',
						use: 'address-icon',
						x: 145,
						y: 130,
						width: 15,
						height: 20
					},
					{
						type: 'textarea',
						text: this.currectData.province + this.currectData.city + this.currectData.area + this
							.currectData.detailedAddress,
						lineSpace: 1,
						width: 320,
						use: 'address',
						x: 175,
						y: 135,
						size: 14,
						color: '#000'
					},
					{
						type: 'image',
						path: '../../static/imgs/card/phone1.png',
						use: 'phone-icon',
						x: 145,
						y: 163,
						width: 16,
						height: 16
					},
					{
						type: 'text',
						text: this.currectData.phone,
						use: 'phone',
						x: 175,
						y: 167,
						size: 14,
						color: '#000'
					},
					{
						type: 'image',
						path: '../../static/imgs/card/remark1.png',
						use: 'remark-icon',
						x: 145,
						y: 197,
						width: 13,
						height: 16
					},
					{
						type: 'textarea',
						text: this.currectData.remark ? this.currectData.remark : '单击添加备注',
						use: 'remark',
						lineSpace: 2,
						width: 200,
						x: 175,
						y: 200,
						size: 13,
						color: '#000'
					},
					{
						type: 'image',
						path: '../../static/imgs/card/bg3.png',
						use: 'bg1',
						x: 0,
						y: 243,
						width: 370,
						height: 67
					},
					{
						type: 'image',
						path: '../../static/imgs/card/bg4.png',
						use: 'bg2',
						x: 355,
						y: 243,
						width: 150,
						height: 67
					},
					{
						type: 'image',
						path: '../../static/imgs/card/home.png',
						use: 'home',
						x: 65,
						y: 260,
						width: 33,
						height: 33
					},
					{
						type: 'text',
						text: this.currectData.classifyName ? this.currectData.classifyName : '默',
						use: 'classify',
						x: 145,
						y: 263,
						size: 34,
						color: '#fff'
					},
					{
						type: 'image',
						path: '../../static/imgs/card/share.png',
						use: 'share',
						x: 235,
						y: 263,
						width: 33,
						height: 28
					},
				]
				console.log(this.currectData.cuttentTemplate, 111111)
				for (var i = 0; i < data.length; i++) {
					switch (this.currectData.cuttentTemplate) {
						case '2':
							if (data[i].use === 'name' ||
								data[i].use === 'companyName' ||
								data[i].use === 'address-icon' ||
								data[i].use === 'phone-icon' ||
								data[i].use === 'remark-icon') {
								data[i].x = 30
							}
							if (data[i].use === 'address-icon') {
								data[i].path = '../../static/imgs/card/address3.png'
							}
							if (data[i].use === 'phone-icon') {
								data[i].path = '../../static/imgs/card/phone3.png'
							}
							if (data[i].use === 'remark-icon') {
								data[i].path = '../../static/imgs/card/remark3.png'
							}
							if (data[i].use === 'post') {
								data[i].x = 100
							}
							if (data[i].use === 'address' ||
								data[i].use === 'phone' ||
								data[i].use === 'remark') {
								data[i].x = 60
							}
							if (data[i].use == 'head') {
								data[i].x = 370
							}
							break;
						case '3':
							if (data[i].use === 'name') {
								data[i].x = 30
								data[i].y = 150
							}
							if (data[i].use === 'companyName') {
								data[i].x = 30
								data[i].y = 180
							}
							if (data[i].use === 'post') {
								data[i].x = 100
								data[i].y = 153
							}
							if (data[i].use === 'address-icon' ||
								data[i].use === 'phone-icon' ||
								data[i].use === 'remark-icon') {
								data[i].x = 170
							}
							if (data[i].use === 'address-icon') {
								data[i].y = 150
							}
							if (data[i].use === 'address') {
								data[i].y = 155;
								data[i].width = 300
							}
							if (data[i].use === 'phone-icon') {
								data[i].y = 180
							}
							if (data[i].use === 'phone') {
								data[i].y = 183
							}
							if (data[i].use === 'remark-icon') {
								data[i].y = 210
							}
							if (data[i].use === 'remark') {
								data[i].y = 212
							}
							if (data[i].use === 'address' ||
								data[i].use === 'phone' ||
								data[i].use === 'remark') {
								data[i].x = 190
							}
							break;
						case '4':
							if (data[i].use === 'name' ||
								data[i].use === 'companyName') {
								data[i].x = 30
							}
							if (data[i].use === 'companyName') {
								data[i].y = 90
							}
							if (data[i].use === 'post') {
								data[i].x = 100
							}
							if (data[i].use == 'head') {
								data[i].x = 25
								data[i].y = 120
							}
							if (data[i].use === 'address-icon') {
								data[i].path = '../../static/imgs/card/address2.png'
							}
							if (data[i].use === 'phone-icon') {
								data[i].path = '../../static/imgs/card/phone2.png'
							}
							if (data[i].use === 'remark-icon') {
								data[i].path = '../../static/imgs/card/remark2.png'
							}
							if (data[i].use === 'address-icon' ||
								data[i].use === 'phone-icon' ||
								data[i].use === 'remark-icon') {
								data[i].x = 170
							}
							if (data[i].use === 'address') {
								data[i].width = 300
							}
							if (data[i].use === 'address' ||
								data[i].use === 'phone' ||
								data[i].use === 'remark') {
								data[i].x = 190
							}
							break;
						default:
					}
				}
				this.canvasData = {
					clicknum: this.clicknum++,
					list: data,
					page: 'cardHolder'
				}

			},
			async delConfirm() {
				uni.showLoading({
					title: '数据加载中',
					mask: true
				})

				for (var i = 0; i < this.checkedList.length; i++) {
					await this.$request.baseRequest('admin.unimall.cardHolderInfo', 'delete', {
						id: this.checkedList[i].data.id,
					}, failres => {
						console.log('res+++++', failres.errmsg)
						uni.showToast({
							icon: "none",
							title: failres.errmsg,
							duration: 3000
						});
					}).then(res => {
						console.log(i, this.checkedList.length - 1)
						if (i == this.checkedList.length - 1) {
							uni.hideLoading()
							uni.showToast({
								icon: "success",
								title: '删除成功！',
								duration: 2000
							});
							this.delShow = false
							this.islongPress = false
							this.mescroll.resetUpScroll()
						}
					})
				}
			},
			del() {

				if (this.checkedList.length > 0) {
					this.delShow = true
				} else {
					uni.showToast({
						icon: "none",
						title: '请勾选后再进行删除',
						duration: 2000
					});
				}
			},

			longpress() {
				console.log("长按事件");
				this.islongPress = true;

			},
			checkboxChange(e, i) {
				console.log(e, i)
				if (i === '') {
					this.checked = !this.checked;
					this.cardHolderList.map(item => item.checked = this.checked);
					this.$forceUpdate()
					var checkList = this.cardHolderList.filter((item) => {
						return item.checked == true
					})
					var data = checkList.map((item, index) => {
						return {
							index: index,
							data: item
						}
					})
					this.checkedList = JSON.parse(JSON.stringify(data))
				} else {
					if (e[0]) {
						this.checkedList.push({
							index: i,
							data: this.cardHolderList[i]
						})
					} else {
						var index = this.checkedList.findIndex((item) => {
							return item.index == i
						})
						this.checkedList.splice(index, 1)
					}
					if (this.checkedList.length != this.cardHolderList.length) {
						this.checked = false
					}
				}
				console.log(this.checkedList)
			},
			remarkConfirm() {
				if (this.remark) {
					this.currectData.remark = this.remark
					this.$request.baseRequest('admin.unimall.cardHolderInfo', 'update', {
						cardHolderInfo: JSON.stringify(this.currectData),
					}, failres => {
						uni.showToast({
							icon: "none",
							title: failres.errmsg,
							duration: 3000
						});
					}).then(res => {
						uni.showToast({
							icon: "success",
							title: '修改备注成功！',
							duration: 2000
						});
						this.remark = ''
						this.modalShow = false
						this.mescroll.resetUpScroll()
					})
				}
			},
			remarkEdit(item) {
				this.currectData = item
				this.remark = this.currectData.remark
				this.modalShow = true
			},
			switchType(item) {
				this.type = 2
				this.typeColumns[0].shift()
				this.currectData = item
				this.isShowType = true
			},
			typeConfirm(e) {
				if (this.type == 2) {
					console.log(e.value[0].circleName)
					this.$set(this.currectData, 'classify', e.value[0].circleName)
					this.$request.baseRequest('admin.unimall.cardHolderInfo', 'update', {
						cardHolderInfo: JSON.stringify(this.currectData),
					}, failres => {
						uni.showToast({
							icon: "none",
							title: failres.errmsg,
							duration: 3000
						});
					}).then(res => {
						uni.showToast({
							icon: "success",
							title: '修改分类成功！',
							duration: 2000
						});
						this.mescroll.resetUpScroll()
					})
				} else {
					this.typeName = e.value[0].circleName
					if (this.typeName == "全部分类") this.typeName = ''
					this.mescroll.resetUpScroll()
				}
				this.isShowType = false
			},
			async upCallback(page) {
				if (uni.getLaunchOptionsSync().scene == 1154) {
					uni.showToast({
						title: '请前往小程序查看名片',
						icon: 'none',
						duration: 10000
					});
				} else {
					var that = this
					if (uni.getStorageSync("userInfo").id) {
						uni.showLoading({
							title: '数据加载中'
						})
						await this.$request.baseRequest('admin.unimall.cardHolderInfo', 'list', {
							page: page.num,
							limit: page.size,
							commonId: uni.getStorageSync("userInfo").id,
							searchContent: this.searchVal,
							classify: this.typeName
						}, failres => {
							uni.showToast({
								icon: "none",
								title: failres.errmsg,
								duration: 3000
							});
							uni.hideLoading()
						}).then(res => {
							if (page.num == 1) this.cardHolderList = [];
							let curPageLen = res.data.items.length;
							let totalPage = res.data.total;
							for (var i = 0; i < res.data.items.length; i++) {
								res.data.items[i].checked = false
								if (res.data.items[i].classify) {
									res.data.items[i].classifyName = res.data.items[i].classify.substring(0,2)
								}
							}
							this.cardHolderList = res.data.items
							this.loading = false
							this.$nextTick(() => {
								that.mescroll.endBySize(curPageLen, totalPage)
							});

							uni.hideLoading()
						})

						await this.$request.baseRequest('admin.unimall.cardClassifyInfo', 'list', {
							page: 1,
							limit: 9999,
							commonId: uni.getStorageSync("userInfo").id
						}, failres => {
							uni.showToast({
								icon: "none",
								title: failres.errmsg,
								duration: 3000
							});
						}).then(res => {
							res.data.items.unshift({
								circleName: '全部分类'
							})
							this.typeColumns = [res.data.items]
						})
						//清楚扫码添加跳页刷新数据状态开关
						uni.setStorageSync("scanAddCardToIndex", 0)
					}

				}
			},
			toNotice() {
				if (this.userInfo.phone) {
					uni.navigateTo({
						url: "/pageA/cardHolder/notice"
					})
				} else {
					this.showAuthorizePhone = true
				}
			},
			micOpen: function() {
				if (this.userInfo.phone) {
					uni.navigateTo({
						url: "/pageA/cardHolder/search?mic=1"
					})
				} else {
					this.showAuthorizePhone = true
				}

			},
			uploadFile: function(tempFilePath) {
				return new Promise((resolve, reject) => {
					//调用你的接口把音频文件转为文字
					this.$minApi.upload('txasr/SentenceRecognition', null, tempFilePath)
						.then(res => {
							if (res.code == 1) {
								resolve(res.data.Result);
							} else {
								reject(e);
							}
						})
						.catch(e => {
							reject(e);
						});
				});
			},
			search() {
				if (this.userInfo.phone) {
					uni.navigateTo({
						url: "/pageA/cardHolder/search"
					})
				} else {
					this.showAuthorizePhone = true
				}
			},
			selectType() {
				this.isShowType = true
				this.type = 1
			},
			scan() {
				if (uni.getStorageSync("userInfo")) {
					uni.scanCode({
						success: function(res) {
							console.log('条码类型：' + res.scanType);
							console.log('条码内容：' + res.result);
							// console.log(res.result.split("=")[1],res.result.split("=")[1].indexOf(','),res.result.split("=")[1].split(",")[0])
							// var cardId = res.result.split("=")[1].split(",")[0]
							var cardId = res.result.split("=")[1]
							that.$request.baseRequest('admin.unimall.cardHolderInfo', 'getAdded', {
								commonId: uni.getStorageSync("userInfo").id,
								cardId: cardId
							}, failres => {
								uni.showToast({
									icon: "none",
									title: failres.errmsg,
									duration: 3000
								});
							}).then(res1 => {
								uni.navigateTo({
									url: "/pageA/cardHolder/scanCodeAddCard?id=" + res.result
										.split("=")[1]
								})
							})
					
						}
					});
				} else {
					this.showAuthorizePhone = true
				}
			},
			toMap(item) {
				uni.navigateTo({
					url: "/pageA/cardHolder/map?val=" + JSON.stringify(item)
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		// padding: 0 20rpx;
	}

	.u-nav-slot {
		/deep/.u-badge {
			position: absolute;
			top: 0;
			right: 6rpx;
		}
	}

	.content1 {
		padding: 20rpx;
		background-color: #112253;

		.search {
			color: #9199af;
			background: #22325f;
			border-radius: 50rpx;
			width: 93%;
			padding: 10rpx 0 10rpx 30rpx;
			box-sizing: border-box;
			margin-right: 20rpx;
		}

		.left {
			width: 80%;

			text {
				height: 46rpx;
				white-space: nowrap;
				overflow: scroll;
				position: relative;
				margin-left: 20rpx;
			}
		}

		.right {
			// width:14%;
			margin-right: 20rpx
		}
	}

	.content2 {
		background: #112253;
		color: #fff;
		padding: 20rpx 20rpx 200rpx 20rpx;
		border-radius: 0px 0px 20px 20px;

	}

	.content3 {
		// padding:0 20rpx;
		// position: relative;
		// top: 376rpx;
		margin: 0 20rpx;
		background: #f5f5f5;
		// height: 1000px;
		border-radius: 30rpx;

		.item {
			border-radius: 30rpx;
		}

		.style1 {
			display: flex;
			width: 100%;
			margin-bottom: 90rpx;

			.left {
				width: 132rpx;
				height: 132rpx;
				border-radius: 50%;
			}

			.right {
				margin-left: 40rpx;

				.row1 {
					.name {
						font-size: 38rpx;
						font-weight: bold;
						color: #040000;
						margin-right: 20rpx;
					}

					.post {
						font-size: 26rpx;
						font-weight: 500;
						color: #666666;
					}
				}

				.row2 {
					font-size: 24rpx;
					font-weight: bold;
					color: #323333;
				}

				.row3 {
					margin-top: 30rpx;
					font-size: 24rpx;
					font-weight: 500;
					color: #323333;

				}

				.row2,
				.row4 {
					margin-top: 10rpx;
				}
			}
		}

		.style2 {
			padding: 15rpx;
			margin-bottom: 80rpx;

			.top {
				.row1 {
					.name {
						font-size: 38rpx;
						font-weight: bold;
						color: #040000;
						margin-right: 20rpx;
					}

					.post {
						font-size: 26rpx;
						font-weight: 500;
						color: #666666;
					}
				}

				.row2 {
					margin-top: 10rpx;
					font-size: 24rpx;
					font-weight: bold;
					color: #323333;
				}
			}

			.bottom {
				margin-top: 20rpx;

				.left {
					margin-right: 40rpx;

					.row1 {
						.name {
							font-size: 38rpx;
							font-weight: bold;
							color: #040000;
							margin-right: 20rpx;
						}

						.post {
							font-size: 26rpx;
							font-weight: 500;
							color: #666666;
						}
					}

					.row2 {
						margin-top: 10rpx;
						font-size: 26rpx;
						font-weight: bold;
						color: #323333;
					}

				}

				.right {

					.row1,
					.row2 {
						font-size: 24rpx;
						font-weight: 500;
						color: #323333;
					}

					.row2,
					.row4 {
						margin-top: 10rpx;
					}
				}
			}

		}

		.card-list-item {
			width: calc(100%);
			// left:-20rpx;
			position: relative;
			border-radius: 30rpx;
			padding: 40rpx;
			box-sizing: border-box;
			margin-bottom: 20rpx;
			.car-bottom {
				position: absolute;
				bottom: 63rpx;
				left: 0rpx;
				bottom: 0rpx;
				width: 100%;
				background: url("../../static/imgs/card/bg5.png") no-repeat center;
				background-size: 100% 100%;
				height: 80rpx;

				// width: calc(100% - 40rpx);
				.left {
					// top: -40rpx;
					position: absolute;
					padding: 20rpx 0;
					width: 70vw;
					height: 80rpx;
					box-sizing: border-box;
					display: flex;
					align-items: center;
					justify-content: space-evenly;
					left: 0px;

					// background-size: 100% 100%;
					overflow-y: hidden;
				}
			}

			.left {
				width: 30%;

				.top {

					margin-bottom: 20rpx;
				}

				.img {
					width: 80%;
				}

				.bottom {}
			}

			.right {
				.row1 {
					.line {
						width: 1px;
						height: 20px;
						margin: 0 20rpx;
						background: black;
					}
				}
			}
		}

	}

	.slot-content {
		width: 100%;
	}

	.shade {
		background: rgba(0, 0, 0, 0.2);
		width: 100%;
		height: 100vh;
		position: fixed;
		top: 0;
		left: 0;
	}

	.qrCode {
		position: absolute;
		top: 50%;
		left: 0;
		right: 0;
		transform: translateY(-50%);
		text-align: center;
	}

	/deep/.u-checkbox-group {

		position: absolute;
		right: 20px;
		z-index: 99;
		top: 30px;
	}

	.del-style {
		background: #fff;
		padding: 20rpx;
		width: calc(100vw - 40rpx);
		position: fixed;
		display: flex;
		z-index: 100;
		justify-content: space-between;
		bottom: 0;
		font-size: 28rpx;

		.right {
			color: #344577;
		}
	}

	.del,
	.qx {
		padding: 10rpx;
	}

	.del {
		color: #FF4040;
	}

	.qx {
		color: #666;
		bottom: 0
	}

	.share-content {
		display: flex;
		padding: 20rpx;
		justify-content: center;
		font-size: 24rpx;

		button {
			background: transparent;
			line-height: 1;
		}

		button:after {
			border: none;
		}
	}

	.sq-view {
		padding: 60rpx 80rpx 80rpx 80rpx;

		.text {
			display: flex;
			justify-content: center;
			font-size: 32rpx;
			font-weight: bold;
			color: #1A1A1A;
			margin-bottom: 60rpx;
		}

		.confirm {
			display: flex;
			justify-content: center;
			color: white;
			// margin: 40rpx auto;
			background: #18254C;
			border-radius: 20rpx;
		}
	}

	.icon-text {
		margin-left: 10px;
		font-size: 26rpx;
		font-weight: 500;
		color: #323333;
	}

	/deep/.u-checkbox__icon-wrap.u-checkbox__icon-wrap--square {
		border-color: #D7DEEB !important;
	}

	.wrap {
		position: fixed;
		top: 0;
		width: 100%;
	}

	/deep/.mescroll-body {
		min-height: 558px !important;
		height: 558px !important;
		background-color: green;
		overflow: scroll !important;
	}

	.auth-btncard {
		display: flex !important;
		justify-content: space-between !important;

		.btn-unok {
			width: 40%;
		}

		.btn-ok {
			width: 40%;
		}
	}

	.auth-card {
		text-align: center;

		.avatar-img {
			width: 250rpx;
		}

		.title {
			font-size: 20rpx;
		}

		.content {
			font-size: 32rpx;
			font-weight: bold;
			color: #1A1A1A;
			margin-bottom: 30rpx;
		}
	}

	.avatar-wrapper {
		color: #333 !important;
		border: none !important;
		border-radius: 0 !important;
		background-color: transparent !important;
		padding: 0;
	}

	.avatar-wrapper::after {
		border: none !important;
	}

	.avatar {
		width: 100rpx;
		height: 100rpx;
		overflow: hidden;
		border-radius: 100%;
	}

	/deep/.u-popup__content {
		border-radius: 20rpx !important;
	}
</style>