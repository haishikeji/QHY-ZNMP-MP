<template>
	<view class="card">
		<view class="card-list" v-if="list">
			<view v-for='(item,index) in list' class="item flex">
				<view class="card-list-item"
					:style="'background:url('+item.currentBackground+');background-size:100% 100%;width:100%'">
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
							<view class="row3 flex">
								<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ item.province }}{{ item.city }}{{ item.area }}
							</view>
							<view class="row4 flex">
								<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ item.phone }}
							</view>
							<!-- <view class="">
								备注
							</view> -->
						</view>
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
							<view class="row3 flex">
								<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ item.province }}{{ item.city }}{{ item.area }}
							</view>
							<view class="row4 flex">
								<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ item.phone }}
							</view>
							<!-- <view class="">
								备注
							</view> -->
						</view>
						<view class="left">
							<u--image :showLoading="true" :src="item.headSculpture" width="66px" height="66px"
								shape="circle" v-if="item.headSculpture"></u--image>
							<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px" height="66px"
								shape="circle" v-else></u--image>
						</view>
					</view>
					<view class="card-content style2" v-if="item.cuttentTemplate==3">
						<view class="top">
							<u--image :showLoading="true" :src="item.headSculpture" width="66px" height="66px"
								shape="circle" v-if="item.headSculpture"></u--image>
							<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px" height="66px"
								shape="circle" v-else></u--image>
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
								<view class="row1 flex">
									<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
										color="#5e6d82"></uni-icons>{{ item.province }}{{ item.city }}{{ item.area }}
								</view>
								<view class="row2 flex">
									<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
										color="#5e6d82"></uni-icons>{{ item.phone }}
								</view>
							</view>
						</view>
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
								<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px" height="66px"
									shape="circle" v-else></u--image>
							</view>
							<view class="right">
								<view class="row1 flex">
									<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
										color="#5e6d82"></uni-icons>{{ item.province }}{{ item.city }}{{ item.area }}
								</view>
								<view class="row2 flex">
									<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
										color="#5e6d82"></uni-icons>{{ item.phone }}
								</view>
							</view>
						</view>
					</view>
					<view class="car-bottom flex">
						<view class="left" :style='styleList[0]'>
							
						</view>
						<view class="right" :style='styleList[1]'>
					
						</view>
					</view>
				</view>
			</view>
		</view>

		<view class="card-obj" v-if="obj">
			<view class="card-list-item" :style="obj.currentBackground?'background:url('+obj.currentBackground+');background-size:100% 100%':''">
				<view class="card-content style1" v-if="obj.cuttentTemplate==1">
					<view class="left">
						<u--image :showLoading="true" :src="obj.headSculpture" width="66px" height="66px"
							shape="circle"></u--image>
					</view>
					<view class="right">
						<view class="row1">
							<text class="name">{{obj.name}}</text>
							<text class="post">{{obj.post}}</text>
						</view>
						<view class="row2">
							{{obj.companyName}}
						</view>
						<view class="row3 flex">
							<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
								color="#5e6d82"></uni-icons>{{ obj.province }}{{ obj.city }}{{ obj.area }}
						</view>
						<view class="row4 flex">
							<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
								color="#5e6d82"></uni-icons>{{ obj.phone }}
						</view>
					</view>
				</view>
				<view class="card-content style1 flex-between" v-if="obj.cuttentTemplate==2">
					<view class="right">
						<view class="row1">
							<text class="name">{{obj.name}}</text>
							<text class="post">{{obj.post}}</text>
						</view>
						<view class="row2">
							{{obj.companyName}}
						</view>
						<view class="row3 flex">
							<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
								color="#5e6d82"></uni-icons>{{ obj.province }}{{ obj.city }}{{ obj.area }}
						</view>
						<view class="row4 flex">
							<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
								color="#5e6d82"></uni-icons>{{ obj.phone }}
						</view>
					</view>
					<view class="left">
						<u--image :showLoading="true" :src="obj.headSculpture" width="66px" height="66px" shape="circle"
							v-if="obj.headSculpture"></u--image>
						<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px" height="66px" shape="circle"
							v-else></u--image>
					</view>
				</view>
				<view class="card-content style2" v-if="obj.cuttentTemplate==3">
					<view class="top">
						<u--image :showLoading="true" :src="obj.headSculpture" width="66px" height="66px" shape="circle"
							v-if="obj.headSculpture"></u--image>
						<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px" height="66px" shape="circle"
							v-else></u--image>
					</view>
					<view class="bottom flex">
						<view class="left">
							<view class="row1">
								<text class="name">{{obj.name}}</text>
								<text class="post">{{obj.post}}</text>
							</view>
							<view class="row2">
								{{obj.companyName}}
							</view>
						</view>
						<view class="right">
							<view class="row1 flex">
								<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ obj.province }}{{ obj.city }}{{ obj.area }}
							</view>
							<view class="row2 flex">
								<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ obj.phone }}
							</view>
						</view>
					</view>
				</view>
				<view class="card-content style2" v-if="obj.cuttentTemplate==4">
					<view class="top">
						<view class="row1">
							<text class="name">{{obj.name}}</text>
							<text class="post">{{obj.post}}</text>
						</view>
						<view class="row2">
							{{obj.companyName}}
						</view>

					</view>
					<view class="bottom flex">
						<view class="left">
							<u--image :showLoading="true" :src="obj.headSculpture" width="66px" height="66px"
								shape="circle" v-if="obj.headSculpture"></u--image>
							<u--image :showLoading="true" src="/static/imgs/card/defaulthead.png" width="66px"
								height="66px" shape="circle" v-else></u--image>
						</view>
						<view class="right">
							<view class="row1 flex">
								<uni-icons type="map-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ obj.province }}{{ obj.city }}{{ obj.area }}
							</view>
							<view class="row2 flex">
								<uni-icons type="phone-filled" size="20" style="margin-right: 20rpx;"
									color="#5e6d82"></uni-icons>{{ obj.phone }}
							</view>
						</view>
					</view>
				</view>
				<view class="car-bottom flex">
					<view class="left" :style='styleList[0]'>
					</view>
					<view class="right" :style='styleList[1]'>
					</view>
				</view>

			</view>
		</view>




	</view>
</template>

<script>
	export default {
		name: "Card",
		props: {
			obj: {
				type: Object,
				required: true
			},
			list: {
				type: Array,
				required: true
			},
			styleList: {
				type: Array,
				required: true
			},
			page: {
				type: String,
				required: true
			}
		},
		data() {
			return {

			};
		},
		watch: {
			list(newVal, oldVal) {

			}
		},
		mounted() {},

		methods: {}
	}
</script>

<style lang="scss" scoped>
	.style1 {
		display: flex;
		padding: 20rpx 30rpx;
		width: 85%;
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
				margin-top: 20rpx;
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
		}
	}

	.style2 {
		padding: 50rpx 30rpx;
		margin-bottom: 100rpx;

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
			margin-top: 48rpx;

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
			}
		}

	}

	.card-list-item {
		position: relative;
		border-radius: 30rpx;
		padding: 40rpx 40rpx 0 40rpx;
		box-sizing: border-box;

		.car-bottom {
			position: relative;
			bottom: 60rpx;
			left: -20rpx;
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
</style>