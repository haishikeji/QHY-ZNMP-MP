<template>
	<view>
		<u-navbar placeholder title="我的证件" @leftClick="leftClick" bgColor="#112253" titleStyle="color:#fff"
			leftIconColor="#fff">
		</u-navbar>
		<view class="flex content1 flex-between">
			<view @click='addclick' class="button">
				<!-- <u--image :showLoading="true" src="../../static/imgs/mySet/add.png" width="80px" height="80px"></u--image> -->
				<image src="../../static/imgs/mySet/add.png" mode="widthFix" class="btn-img"></image>
				新增
			</view>
			<view @click='share' class="button">
				<image src="../../static/imgs/mySet/share1.png" mode="widthFix" class="btn-img"></image>
				分享
			</view>
			<view @click='imageOCR' class="button">
				<image src="../../static/imgs/mySet/shibie.png" mode="widthFix" class="btn-img"></image>
				识别号码
			</view>
		</view>
		<view v-if='addstatus' class="content2">
			<view class="flex flex-between">
				<u--input border="bottom" v-model="cardData.certificateName" placeholder="输入证件名称" focus></u--input>
				<view @click='add' class="submit">提交</view>
			</view>
			<view class='add-image'>
				<u-upload @afterRead="afterRead" :fileList="fileList1" @delete="deletePic" name="1" multiple
					:maxCount="1" height='190' width='360'>
					<view class="bgc">
						<image class='circle' style='width:148rpx;height:148rpx;' src="../../static/imgs/mySet/uploadbg.png" mode=""></image>
						<!-- <view class="circle">
							<view class="add">
								+
							</view>
						</view> -->
					</view>
				</u-upload>
			</view>
		</view>
		<view class="content3" style="background-color: #F7F7F7;">
			<mescroll-body v-if='!addstatus&&cardList.length==0||cardList.length>0' ref="mescrollRef"
				@init="mescrollInit" @up="upCallback" @down="downCallback" :up="upOption" :down="downOption">
				<view v-for='(item,index) in cardList' class="">
					<view class="flex flex-between row">
						<view v-if='!item.editstatus' class="left flex">
							<view class="name-text">
								{{item.certificateName}}
							</view>
							<text v-if='item.personNo'>({{item.personNo1}})</text>
							<image src="../../static/imgs/mySet/copy.png" mode="widthFix" style="width: 28rpx;" v-if='item.personNo' @click='clip(0,item)'></image>
							<!-- <u-icon v-if='item.personNo' @click='clip(0,item)' name="file-text-fill" color="#2979ff"
								size="28"></u-icon> -->
							<text v-if='item.bankNo' style="margin:0 20rpx ;">{{item.bankNo1}}</text>
							<image src="../../static/imgs/mySet/copy.png" mode="widthFix" style="width: 28rpx;" v-if='item.bankNo' @click='clip(1,item)'></image>
							<!-- <u-icon v-if='item.bankNo' @click='clip(1,item)' name="file-text-fill" color="#2979ff"
								size="28"></u-icon> -->
						</view>
						<u--input @input='certificateNameInput($event,item)' border="bottom" v-else
							v-model="item.certificateName" placeholder="输入证件名称"></u--input>
						<view class="flex" v-if="item.editstatus">
							<view @click='qxEdit(item)' :class="!item.editstatus?'edit':'finished'"
								style="margin:0 20rpx;">取消</view>
							<view @click='edit(item)' :class="!item.status?'edit':'finished'">完成</view>
						</view>
						<view class="flex" v-if="!item.editstatus">
							<view @click="del(item)" :class="!item.editstatus?'edit':'finished'"
								style="margin:0 20rpx 0 0;">删除</view>
							<view @click='edit(item)' class="finished">编辑</view>
						</view>
					</view>
					<view class="row2" v-if='!item.editstatus'>
						<image @click='previewImage(item.certificateImage)' :src="item.certificateImage" class="img" mode="aspectFill" style="width: 95%;"></image>
						<view class="right">
							<u-checkbox-group v-model="item.checked" placement="column"
								@change="checkboxChange($event,index,item)">
								<u-checkbox iconColor='#4977FC' activeColor="#fff" :name='index+1'
									:customStyle="{marginBottom: '8px'}">
								</u-checkbox>
							</u-checkbox-group>
							<!-- <image src="../../static/imgs/mySet/del1.png" mode="widthFix" @click="del(item)" style="width: 28rpx;"></image> -->
						</view>
					</view>
					<view v-else style='padding-left:20rpx;'>
						<u-upload @afterRead="afterRead" :fileList="fileList2" @delete="deletePic" name="2" multiple
							:maxCount="1" height='190' width='340'>
							<!-- <view class="">
								123
							</view> -->
							<!-- <image src="https://cdn.uviewui.com/uview/demo/upload/positive.png" 
								mode="widthFix" style="width: 250px;height: 150px;"></image> -->
						</u-upload>
					</view>
				</view>
			</mescroll-body>
		</view>

		<u-picker :immediateChange="true" @cancel='show=false' @confirm='pickerConfirm' title='识别类型' :show="show"
			:columns="columns"></u-picker>
		<u-popup :show="popupshow" :closeOnClickOverlay='true' :overlayOpacity='0.4' @close='handleHiddenShare'
			:round="10" mode="bottom">
			<view>
				<view class="share-content">
					<view class="block" @click="toUrl()">
						<button class="moment">
							<view class="iconfont icon-pengyouquan"></view>
							<view style="font-size: 26rpx;">分享朋友圈</view>
						</button>
					</view>
					<view class="block">
						<button class="shareBtn" type="default" data-name="shareBtn" open-type="share">
							<view class="iconfont icon-weixin"></view>
							<view style="font-size: 26rpx;">分享微信好友</view>
						</button>
					</view>
				</view>
			</view>
		</u-popup>
		<u-modal @cancel='modalshow=false' confirmText='删除' showCancelButton='true' @confirm='delConfirm'
			:show="modalshow" title="提示" content='确定删除证件？'></u-modal>
		<u-modal @cancel='credentialscancel' cancelText='重新识别' confirmText='确认号码' showCancelButton='true'
			@confirm='credentialsConfirm' :show="credentialsShow" title="提示">
			<view class="slot-content">
				证件号码
				<u--input v-if='shibie=="识别身份证号"' v-model="credentials.recPersonNo" placeholder="输入证件名称"
					border="none"></u--input>
				<u--input v-if='shibie=="识别银行卡号"' v-model="credentials.bankNo" placeholder="输入证件名称"
					border="none"></u--input>
			</view>
		</u-modal>
		<u-modal :show="modalstatusshow" confirmText='继续返回' cancelText='取消' @confirm='confirm' @cancel='cancel'
			showCancelButton='true' title="提示" content='有证件处于编辑状态未提交。'></u-modal>
		<u-toast ref="uToast"></u-toast>
		<canvas style="position: absolute; top: -1000px; left: -1000px; width: 600px; height: 400px; background: #000;"
			canvas-id="canvas"></canvas>
		<point-share @close='showShare=false' :show='showShare'></point-share>
	</view>
</template>

<script>
	import uploadImage from '@/components/ossutil/uploadFile.js';
	import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
	import PointShare from '../../components/point-share/index.vue';
	export default {
		mixins: [MescrollMixin],
		components: {
			PointShare
		},
		data() {
			return {
				downOption: {
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
				show: false,
				modalshow: false,
				modalstatusshow: false,
				popupshow: false,
				columns: [
					['识别身份证号', '识别银行卡号']
				],
				showShare: false,
				userInfo: {},
				cardData: {},
				cardList: [],
				fileList1: [],
				fileList2: [],
				checkedList: [],
				addstatus: false,
				id: '',
				credentialsShow: false,
				credentials: {},
				shibie: '',
				shibieList: [],
				canReset: false,
				shareimg: ''
			};
		},
		onLoad() {

			// this.getList()
		},
		onShow() {
			this.userInfo = uni.getStorageSync("userInfo")
			this.cardData.commonId = uni.getStorageSync("userInfo").id
			this.$nextTick(function() {
				this.canReset && this.mescroll.resetUpScroll() // 重置列表数据为第一页  
				this.canReset && this.mescroll.scrollTo(0, 0) // 重置列表数据为第一页时,建议把滚动条也重置到顶部,避免无法再次翻页的问题  
				this.canReset = true // 过滤第一次的onShow事件,避免初始化界面时重复触发upCallback, 无需配置auto:false
			});
			this.cutShareImg()
		},
		onShareAppMessage(res) {

			let that = this;
			//生成名片图片
			let imageUrl = this.poster
			console.log("imageUrl", uni.getStorageSync('checkedList'))
			if (res.from === 'button') {
				return {
					title: '证件分享',
					path: '/pages/mySet/lookCard?dataList=' + uni.getStorageSync('checkedList'),
					imageUrl: this.shareimg,
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
			previewImage(src) {
				console.log(src)
				//预览图片
				uni.previewImage({
					urls: [src], // 需要预览的图片链接列表，可以写你图片链接的数组或者跟我一样，就写一个单独的链接
					current: src // 为当前显示图片的链接/索引值，不填或填写的值无效则为 urls 的第一张
				});
			},
			toUrl() {
				this.popupshow = false
				this.showShare = true
			},
			cutShareImg() {
				let that = this;
				uni.getImageInfo({
					src: 'https://taohaoliang.oss-cn-beijing.aliyuncs.com/pcfiles/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230506110702.png', // 这里填写网络图片路径 
					success: (res) => {
						let ctx = uni.createCanvasContext('canvas');
						ctx.drawImage(res.path, 0, 0, 480, 300)
						ctx.draw(false, () => {
							uni.canvasToTempFilePath({
								canvasId: 'canvas',
								fileType: 'png',
								success: (res) => {
									//设置分享图片路径
									that.shareimg = res.tempFilePath
									console.log(that.shareimg)
								}
							})
						})
					}
				});
			},
			handleHiddenShare() {
				this.popupshow = false
			},
			certificateNameInput(e, item) {
				console.log(item)
				if (item.certificateName1 != item.certificateName) {
					item.status = true
				} else {
					item.status = false
				}

			},
			qxEdit() {
				this.mescroll.resetUpScroll()
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
			edit(item) {
				if (item.editstatus) {
					if (item.status) {
						this.$request.baseRequest('admin.unimall.certificateManagementInfo', 'update', {
							certificateManagementInfo: JSON.stringify(item)
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
								title: '编辑成功！',
								duration: 2000
							});
							this.modalshow = false
							this.checkedList = []
							this.mescroll.resetUpScroll()
						})
					}
				} else {
					item.editstatus = true
					item.certificateName1 = item.certificateName
					this.fileList2 = [{
						url: item.certificateImage
					}]
				}

			},
			delConfirm() {
				this.$request.baseRequest('admin.unimall.certificateManagementInfo', 'delete', {
					id: this.id
				}, failres => {
					console.log('res+++++', failres.errmsg)
					uni.showToast({
						icon: "none",
						title: failres.errmsg,
						duration: 3000
					});
					uni.hideLoading()
				}).then(res => {

					uni.hideLoading()
					uni.showToast({
						icon: "success",
						title: '删除成功！',
						duration: 2000
					});
					this.modalshow = false
					this.checkedList = []
					this.mescroll.resetUpScroll()
				})
			},
			pickerConfirm(e) {
				this.show = false
				console.log(e)
				uni.showLoading({
					title: '数据加载中',
					mask: true
				})
				var type = '',
					type1 = ''
				this.shibie = e.value[0]
				this.shibieList = JSON.parse(JSON.stringify(this.cardList[this.checkedList[0]]))
				if (e.value[0] == '识别身份证号') {
					type = 'admin.unimall.certificateManagementInfo'
					type1 = 'personShibie'
				} else if (e.value[0] == '识别银行卡号') {
					type = 'admin.unimall.certificateManagementInfo'
					type1 = 'bankShibie'
				}
				this.$request.baseRequest(type, type1, {
					certificateImage: this.cardList[this.checkedList[0]].certificateImage
				}, failres => {
					console.log('res+++++', failres.errmsg)
					uni.hideLoading()
					uni.showToast({
						icon: "none",
						title: failres.errmsg,
						duration: 3000
					});
				}).then(res => {
					console.log(res.data)
					uni.hideLoading()
					uni.showToast({
						icon: "success",
						title: '识别成功！',
						duration: 2000
					});
					this.credentials = res.data
					this.credentialsShow = true
				})
			},
			checkboxChange(e, i) {
				console.log(e[0], i)
				if (e[0]) {
					this.checkedList.push(i)
				} else {
					var index = this.checkedList.findIndex((item) => {
						return item == i
					})
					this.checkedList.splice(index, 1)
				}
				// for(var i=0;i<this.cardList.length;i++){
				// 	console.log(this.cardList[i].checked)
				// 	// if(this.cardList[i].checkedList.length>0){
				// 	// 	this.checkedList.push(i)
				// 	// }
				// }
				console.log(this.checkedList)
			},
			share() {
				if (this.checkedList.length == 0) {
					uni.showToast({
						icon: "none",
						title: '请勾选想要分享的证件！！',
						duration: 2000
					});
					return
				}
				var arr = []
				for (var i = 0; i < this.cardList.length; i++) {
					if (this.checkedList.findIndex((item) => {
							return item == i
						}) != -1) {
						arr.push(this.cardList[i])
					}
				}
				console.log(arr, 111)
				uni.setStorageSync('checkedList', JSON.stringify(arr));
				this.popupshow = true
			},
			imageOCR() {
				console.log(this.cardList, this.checkedList)
				if (this.checkedList.length == 0) {
					uni.showToast({
						icon: "none",
						title: '请勾选想要识别的证件！',
						duration: 2000
					});
					return
				}
				if (this.checkedList.length > 1) {
					uni.showToast({
						icon: "none",
						title: '一次只能勾选一张证件',
						duration: 3000
					});
					return
				}
				this.show = true
			},
			credentialscancel() {
				this.credentialsShow = false
				this.pickerConfirm({
					value: [this.shibie]
				})
			},
			credentialsConfirm() {
				var type = '',
					type1 = ''
				if (this.shibie == '识别身份证号') {
					this.shibieList.personNo = this.credentials.recPersonNo
				} else if (this.shibie == '识别银行卡号') {
					this.shibieList.bankNo = this.credentials.bankNo
				}
				// if(this.shibie=='识别身份证号'){
				// 	type='admin.unimall.certificateManagementInfo'
				// 	type1='personShibie'
				// }else if(this.shibie=='识别银行卡号'){
				// 	type='admin.unimall.certificateManagementInfo'
				// 	type1='bankShibie'
				// }

				this.$request.baseRequest('admin.unimall.certificateManagementInfo', 'update', {
					certificateManagementInfo: JSON.stringify(this.shibieList)
				}, failres => {
					console.log('res+++++', failres.errmsg)
					uni.showToast({
						icon: "none",
						title: failres.errmsg,
						duration: 3000
					});
					uni.hideLoading()
				}).then(res => {
					uni.hideLoading()
					uni.showToast({
						icon: "success",
						title: '编辑成功！',
						duration: 2000
					});
					this.credentialsShow = false
					this.checkedList = []
					this.mescroll.resetUpScroll()
				})
			},
			confirm() {
				uni.navigateBack()
			},
			cancel() {
				this.modalstatusshow = false
			},
			leftClick() {
				if (this.cardData.certificateImage) {
					console.log(22222)
					this.modalstatusshow = true
				} else {
					uni.navigateBack()
				}
			},
			del(item) {
				this.modalshow = true
				this.id = item.id
			},
			addclick() {
				this.addstatus = true
			},
			add() {
				if (!this.cardData.certificateImage) {
					uni.showToast({
						icon: "none",
						title: "请上传证件照片",
						duration: 2000
					});
					return
				}
				uni.showLoading({
					title: '数据加载中'
				})
				this.$request.baseRequest('admin.unimall.certificateManagementInfo', 'add', {
					certificateManagementInfo: JSON.stringify(this.cardData)
				}, failres => {
					console.log('res+++++', failres.errmsg)
					uni.hideLoading()
					uni.showToast({
						icon: "none",
						title: failres.errmsg,
						duration: 3000
					});
				}).then(res => {
					// if (res.errno == 200) {
					uni.hideLoading()
					uni.showToast({
						icon: "success",
						title: '提交成功！',
						duration: 2000
					});
					this.fileList1 = []
					this.addstatus = false
					this.cardData = {
						commonId: uni.getStorageSync("userInfo").id
					}
					this.mescroll.resetUpScroll()

					// }
				})

			},
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
				this.cardData.certificateImage = this[`fileList${event.name}`].toString()
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				// console.log(event,111111,fileListLen)
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					this.cardData.certificateImage = result
					this[`fileList${event.name}`][fileListLen] = {}
					let item = this[`fileList${event.name}`][fileListLen]
					console.log(item)
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
					this.checkedList = []
				}
				console.log(this[`fileList${event.name}`])
			},
			uploadFilePromise(url) {
				console.log(url)
				return new Promise((resolve, reject) => {
					uploadImage(url, 'cardImages/',
						result => {
							console.log(result, 22222)

							resolve(result)
						}
					)
				})
			},
			mescrollInit() {

			},
			downCallback() {
				var that = this
				this.$nextTick(() => {
					// mescroll.endSuccess(data.result);
					that.mescroll.resetUpScroll()
				});

			},
			upCallback(page) {
				var that = this
				uni.showLoading({
					title: '数据加载中'
				})
				this.$request.baseRequest('admin.unimall.certificateManagementInfo', 'list', {
					page: page.num,
					limit: page.size,
					commonId: uni.getStorageSync("userInfo").id
				}, failres => {
					console.log('res+++++', failres.errmsg)
					uni.hideLoading()
					uni.showToast({
						icon: "none",
						title: failres.errmsg,
						duration: 3000
					});
					this.mescroll.endBySize(curPageLen, totalPage)
				}).then(res => {
					console.log(res)
					if (page.num == 1) this.cardList = [];
					let curPageLen = res.data.items.length;
					let totalPage = res.data.total;
					if (res.data.items.length > 0) {
						for (var i = 0; i < res.data.items.length; i++) {
							res.data.items[i].editstatus = false
							res.data.items[i].status = false
							if (res.data.items[i].personNo) res.data.items[i].personNo1 = '****' + (res.data.items[i]
								.personNo.substring(res.data.items[i].personNo.length - 4))
							if (res.data.items[i].bankNo) res.data.items[i].bankNo1 = '****' + (res.data.items[i]
								.bankNo.substring(res.data.items[i].bankNo.length - 4))
						}
					}
					this.cardList = this.cardList.concat(res.data.items)
					this.cardData.certificateName = '证件' + '-' + (this.cardList.length + 1)
					this.$nextTick(() => {
						console.log(that)
						// mescroll.endSuccess(data.result);
						that.mescroll.endBySize(curPageLen, totalPage)
					});

					// if (res.errno == 200) {
					uni.hideLoading()

					// }
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	button::after {
		border: initial;
	}

	.share {
		width: 100%;
		height: 100%;
	}

	.share-box {
		width: 100%;
		height: 100%;
		position: fixed;
		top: 0rpx;
		left: 0rpx;
		bottom: 0rpx;
		right: 0rpx;
		background-color: rgba(0, 0, 0, 0.4);
		transition: .3s;
		z-index: 999;
	}

	.block>button {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		background-color: white;
		border-style: none;
		width: 300rpx;
		height: 180rpx;
		// outline:none;
		// opacity: 0;
	}

	// 进入分享动画
	.share-show {
		transition: all 0.3s ease;
		transform: translateY(0%) !important;
	}

	// 离开分享动画
	.share-item {

		.share-to {
			width: 100%;
			height: 3rem;
			display: flex;
			justify-content: center;
			align-items: center;

			&::after {
				content: '';
				width: 240rpx;
				height: 0rpx;
				border-top: 1px solid #E4E7ED;
				-webkit-transform: scaleY(0.5);
				transform: scaleY(0.5);
				margin-left: 30rpx;
			}

			&::before {
				content: '';
				width: 240rpx;
				height: 0rpx;
				border-top: 1px solid #E4E7ED;
				-webkit-transform: scaleY(0.5);
				transform: scaleY(0.5);
				margin-right: 30rpx;
			}
		}

		.content {
			width: 100%;
			height: auto;
			display: flex;
			flex-wrap: wrap;

			.block {
				width: 50%;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				height: 180rpx;

				image {
					width: 80rpx;
					height: 80rpx;
				}

				text {
					margin-top: 16rpx;
					font-size: 28rpx;
					color: #606266;
				}
			}
		}

		.cancel {
			width: 100%;
			height: 3rem;
			display: flex;
			justify-content: center;
			align-items: center;
			border-top: 1rpx solid #E4E7ED;
		}
	}

	.content1 {

		background-color: #112253;
		padding: 20rpx;
		box-sizing: border-box;
		border-radius: 0px 0px 20rpx 20rpx;

		.button {
			display: flex;
			align-items: center;
			justify-content: center;
			background: #fff;
			color: #333333;
			border-radius: 20rpx;
			padding: 0 24rpx;
			height: 88rpx;
			width: calc(33% - 60rpx);
			font-size: 32rpx;
		}

		.btn-img {
			width: 28rpx;
			margin-right: 18rpx;
			height: auto;
		}
	}

	.content2 {
		padding: 20rpx;

		.bgc {
			background: #fff;
			width: calc(100vw - 40rpx);
			height: 380rpx;
			position: relative;
			border-radius: 30rpx;

			.circle {
				position: absolute;
				top: 50%;
				left: 50%;
				transform: translateX(-50%) translateY(-50%);
				text-align: center;
			}
		}

		.submit {
			padding: 8rpx 20rpx;
			text-align: center;
			background: #112253;
			border-radius: 10rpx;
			font-size: 28rpx;
			font-weight: bold;
			color: #FFFFFF;
			margin-left: 20rpx;
		}
	}

	.content3 {
		.row {
			padding: 20rpx;
			box-sizing: border-box;

			.name-text {
				font-size: 32rpx;
				font-weight: bold;
				color: #1A1A1A;
			}
		}

		.edit {
			padding: 10rpx 14rpx;
			text-align: center;
			background: rgba(17, 34, 83, 0.1);
			border-radius: 10rpx;
			font-size: 28rpx;
			font-weight: bold;
			color: #FFFFFF;
		}

		.finished {
			padding: 10rpx 14rpx;
			text-align: center;
			background: #112253;
			border-radius: 10rpx;
			font-size: 28rpx;
			font-weight: bold;
			color: #FFFFFF;
		}

		.row2 {
			display: flex;
			justify-content: center;
			position: relative;

			.img {
				border-radius: 20rpx;
				height: 380rpx;
			}

			.right {
				position: absolute;
				right: 0;
				height: 100%;
				right: 10px;

				display: flex;
				flex-direction: column;
				justify-content: space-between;
				align-items: center;
				padding: 10px 5px;
				box-sizing: border-box;
			}

			/deep/.u-checkbox__icon-wrap {
				margin-right: 0 !important;
			}
		}
	}

	.add-image {
		background-color: #fff;
		margin-top: 20rpx;
		border-radius: 20rpx;
	}

	.share-content {
		display: flex;
		padding: 20rpx;
		justify-content: center;
		font-size: 24rpx;

		button {
			background: transparent;
			line-height: 1.5;
		}

		button:after {
			border: none;
		}
	}

	.icon-pengyouquan,
	.icon-weixin {
		font-size: 50px;
		color: #33CC00;
	}

	/deep/.u-upload {

		.u-upload__deletable {
			height: 40rpx;
			width: 40rpx;
			line-height: 40rpx;
		}

		.u-upload__deletable__icon {
			top: 8rpx;
		}

		.uicon-close {
			font-size: 20px !important;
		}
	}
</style>