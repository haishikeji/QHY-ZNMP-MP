<template>
	<view class="content">
		<view class="content1 flex">
			<span @click='switchStatus(1)' :class='status==1?"active":""' class='default'>邀请</span>
			<span @click='switchStatus(2)' :class='status==2?"active":""' class='default'>黑名单</span>
		</view>
		<view class="content2 ">
			<view class="row" v-for="(item,index) in cardList" :key="index">
				<view class="card-list-item"
					:style="item.cardManagementInfo.currentBackground?'background:url('+item.cardManagementInfo.currentBackground+');background-size:100% 100%':''">
					<view class="card-content style1" v-if="item.cardManagementInfo.cuttentTemplate==1">
						<view class="left">
							<u--image :showLoading="true" :src="item.cardManagementInfo.headSculpture?item.cardManagementInfo.headSculpture:'/static/imgs/card/defaulthead.png'" width="66px" height="66px"
								shape="circle"></u--image>
						</view>
						<view class="right">
							<view class="row1">
								<text class="name">{{item.cardManagementInfo.name}}</text>
								<text class="post">{{item.cardManagementInfo.post}}</text>
							</view>
							<view class="row2">
								{{item.cardManagementInfo.companyName}}
							</view>
							<view class="row3 flex">
								<u--image :showLoading="true" :src="'/static/imgs/card/address'+item.cardManagementInfo.icon+'.png'" width="13px"
									height="16px"></u--image> <view class='icon-text'>{{ item.cardManagementInfo.province }}{{ item.cardManagementInfo.city }}{{ item.cardManagementInfo.area }}</view>
							</view>
							<view class="row4 flex">
								<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.cardManagementInfo.icon+'.png'" width="12px"
									height="13px"></u--image><view class='icon-text'>{{ item.cardManagementInfo.phone }}</view>
							</view>
							<view class="row4 flex">
								<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.cardManagementInfo.icon+'.png'" width="10px"
									height="12px"></u--image><view class='icon-text'>{{item.cardManagementInfo.remark?item.cardManagementInfo.remark:'--'}}</view>
							</view>
							<!-- <view class="">
								备注
							</view> -->
						</view>
					</view>
					<view class="card-content style1 flex-between" v-if="item.cardManagementInfo.cuttentTemplate==2">
						<view class="right">
							<view class="row1">
								<text class="name">{{item.cardManagementInfo.name}}</text>
								<text class="post">{{item.cardManagementInfo.post}}</text>
							</view>
							<view class="row2">
								{{item.cardManagementInfo.companyName}}
							</view>
							<view class="row3 flex">
								<u--image :showLoading="true" :src="'/static/imgs/card/address'+item.cardManagementInfo.icon+'.png'" width="13px"
									height="16px"></u--image><view class='icon-text'>{{ item.cardManagementInfo.province }}{{ item.cardManagementInfo.city }}{{ item.cardManagementInfo.area }}</view>
							</view>
							<view class="row4 flex">
								<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.cardManagementInfo.icon+'.png'" width="12px"
									height="13px"></u--image><view class='icon-text'>{{ item.cardManagementInfo.phone }}</view>
							</view>
							<view class="row4 flex">
								<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.cardManagementInfo.icon+'.png'" width="10px"
									height="12px"></u--image><view class='icon-text'>{{item.cardManagementInfo.remark?item.cardManagementInfo.remark:'--'}}</view>
							</view>
							<!-- <view class="">
								备注
							</view> -->
						</view>
						<view class="left">
							<u--image :showLoading="true" :src="item.cardManagementInfo.headSculpture" width="66px" height="66px"
								shape="circle" v-if="item.cardManagementInfo.headSculpture"></u--image>
							<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px"
								height="66px" shape="circle" v-else></u--image>
						</view>
					</view>
					<view class="card-content style2" v-if="item.cardManagementInfo.cuttentTemplate==3">
						<view class="top">
							<u--image :showLoading="true" :src="item.cardManagementInfo.headSculpture" width="66px" height="66px"
								shape="circle" v-if="item.cardManagementInfo.headSculpture"></u--image>
							<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px"
								height="66px" shape="circle" v-else></u--image>
						</view>
						<view class="bottom flex">
							<view class="left">
								<view class="row1">
									<text class="name">{{item.cardManagementInfo.name}}</text>
									<text class="post">{{item.cardManagementInfo.post}}</text>
								</view>
								<view class="row2">
									{{item.cardManagementInfo.companyName}}
								</view>
							</view>
							<view class="right">
								<view class="row3 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/address'+item.cardManagementInfo.icon+'.png'" width="13px"
										height="16px"></u--image><view class='icon-text'>{{ item.cardManagementInfo.province }}{{ item.cardManagementInfo.city }}{{ item.cardManagementInfo.area }}</view>
								</view>
								<view class="row4 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.cardManagementInfo.icon+'.png'" width="12px"
										height="13px"></u--image><view class='icon-text'>{{ item.cardManagementInfo.phone }}</view>
								</view>
								<view class="row4 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.cardManagementInfo.icon+'.png'" width="10px"
										height="12px"></u--image><view class='icon-text'>{{item.cardManagementInfo.remark?item.cardManagementInfo.remark:'--'}}</view>
								</view>
							</view>
						</view>
					</view>
					<view class="card-content style2" v-if="item.cardManagementInfo.cuttentTemplate==4">
						<view class="top">
							<view class="row1">
								<text class="name">{{item.cardManagementInfo.name}}</text>
								<text class="post">{{item.cardManagementInfo.post}}</text>
							</view>
							<view class="row2">
								{{item.cardManagementInfo.companyName}}
							</view>
						
						</view>
						<view class="bottom flex">
							<view class="left">
								<u--image :showLoading="true" :src="item.cardManagementInfo.headSculpture" width="66px" height="66px"
									shape="circle" v-if="item.cardManagementInfo.headSculpture"></u--image>
								<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px"
									height="66px" shape="circle" v-else></u--image>
							</view>
							<view class="right">
								<view class="row3 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/address'+item.cardManagementInfo.icon+'.png'" width="13px"
										height="16px"></u--image><view class='icon-text'>{{ item.cardManagementInfo.province }}{{ item.cardManagementInfo.city }}{{ item.cardManagementInfo.area }}</view>
								</view>
								<view class="row4 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/phone'+item.cardManagementInfo.icon+'.png'" width="12px"
										height="13px"></u--image><view class='icon-text'>{{ item.cardManagementInfo.phone }}</view>
								</view>
								<view class="row4 flex">
									<u--image :showLoading="true" :src="'/static/imgs/card/remark'+item.cardManagementInfo.icon+'.png'" width="10px"
										height="12px"></u--image><view class='icon-text'>{{item.cardManagementInfo.remark?item.cardManagementInfo.remark:'--'}}</view>
								</view>
							</view>
						</view>
					</view>
					<view class="car-bottom flex">
						<view class="status-btns" v-if="item.status==0">
							<span class='status-btn' @click='cardExchangeInfoEdit(1,item)'> 拉黑</span>
							<span class='status-btn error' @click='cardExchangeInfoEdit(2,item)'>拒绝</span>
							<span class='status-btn success' @click='cardExchangeInfoEdit(3,item)'>接受</span>
						
						</view>
						<view class="status-btns" v-else>
							<span class='status' v-if="item.status==1">已接受</span>
							<span class='status' v-if="item.status==3">已拒绝</span>
							<span class='status' v-if="item.status==5">已过期</span>
							<span v-if="item.status==7" class='status-btn' @click='cardExchangeInfoEdit(4,item)'>移除</span>
						</view>
					</view>
				</view>
				<view style='margin:0 20rpx;color:#666666;font-size: 26rpx;' class="flex flex-between">
					<span>来自： {{item.circleName}}</span>
					<span> {{parseTime(item.gmtCreate)}}</span>
				</view>
			</view>
		</view>
		<u-toast ref="uToast"></u-toast>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userInfo: {},
				status: 1,
				cardList: [{
					cardManagementInfo: {}
				}],
			};
		},
		onLoad() {
			this.userInfo = uni.getStorageSync("userInfo")
			this.getList()
		},
		methods: {
			switchStatus(status) {
				this.status = status
				this.getList()
			},
			getList() {
				uni.showLoading({
					title: '数据加载中'
				})
				this.$request.baseRequest('admin.unimall.cardExchangeInfo', 'list', {
					receiveId: this.userInfo.id,
					status: this.status
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
					this.cardList = res.data.items
				})
			},
			cardExchangeInfoEdit(type, val) {
				uni.showLoading({
					title: '数据加载中'
				})
				this.$request.baseRequest('admin.unimall.cardExchangeInfo', 'update', {
					cardExchangeInfo: JSON.stringify(val),
					updateFlag: type
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
						title: '操作成功！',
						duration: 2000
					});
					this.getList()
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content1 {
		background: #112253;
		padding: 0rpx 20rpx 160rpx 20rpx;
		box-sizing: border-box;
		border-radius: 0px 0px 20rpx 20rpx;
	}

	
	.default {
		color: rgba(255, 255, 255, 0.5);
		font-size: 26rpx;
		padding: 20rpx;
		position: relative;
	}

	.active {
		color: #fff;
		font-size: 32rpx;
	}

	.active:after {
		content: '';
		display: block;
		width: 10px;
		height: 2px;
		background: #fff;
		position: absolute;
		left: 50%;
		transform: translateX(-50%);
		bottom: 0;
	}

	.content2 {
		position: relative;
		top: -144rpx;
		padding: 20rpx;
		.icon-text{
			margin-left:10rpx;
		}
		.style1 {
			display: flex;
			padding: 20rpx 30rpx;
			width: 85%;
			margin-bottom: 70rpx;

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
					margin-top: 20rpx;
					font-size: 24rpx;
					font-weight: bold;
					color: #323333;
				}

				.row3,.row4 {
					margin-top: 30rpx;
					font-size: 24rpx;
					font-weight: 500;
					color: #323333;

				}
			}
		}

		.style2 {
			padding: 30rpx;

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
					margin-top: 20rpx;
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
						margin-top: 20rpx;
						font-size: 24rpx;
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

					.row2 {
						margin-top: 20rpx;
					}
					.row3,.row4 {
						margin-top: 30rpx;
						font-size: 24rpx;
						font-weight: 500;
						color: #323333;
					
					}
				}
			}

		}
	}

	.card-list {
		padding: 0 20rpx;

		.title-name {
			font-weight: bold;
			color: #19191A;
			margin: 20rpx 0;
		}
	}

	.card-list-item {
		width: calc(100% + 40rpx);
		left: -20rpx;
		position: relative;
		border-radius: 30rpx;
		padding: 40rpx;
		box-sizing: border-box;

		.car-bottom {
			justify-content: flex-end;
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

	.icon-text {
		margin-left: 10px;
		font-size: 24rpx;
		font-weight: 500;
		color: #323333;
	}

	.status-btns {
		margin: 10rpx 0 25rpx;
		
	}

	.status-btns span {
		margin-right: 10rpx;
		border-radius: 10rpx;
		background: #fff;
		border: 1px solid rgba(0, 0, 0, 0.20);
		padding: 10rpx 30rpx;
		font-size: 26rpx;
	}

	.status {
		color: #666;
	}

	.error {
		color: #FF4E4E;
	}

	.success {
		color: #279D2A;
	}
</style>