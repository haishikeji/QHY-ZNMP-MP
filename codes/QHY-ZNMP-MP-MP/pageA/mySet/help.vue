<template>
	<view class='content'>
		<view class="bg"></view>
		<view class=' imgwrap'>
			<image class='img'  src="../../static/imgs/mySet/question1.png" mode=""></image>
			<image class='img'  src="../../static/imgs/mySet/question2.png" mode=""></image>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		}
	}
</script>

<style lang="scss" scoped>
.bg{
	height:162rpx;
	background: linear-gradient(180deg, #112253 0%,  #FCFCFC 100%);
}
// .content{
// 	padding:20rpx;
// }
.imgwrap{
	position:relative;
	top:-132rpx;
	text-align:center;
}
.img{
	width:680rpx;
	height:246rpx;
	margin:20rpx auto;
}
</style>
