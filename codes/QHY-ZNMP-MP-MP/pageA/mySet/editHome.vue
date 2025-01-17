<template>
	<view class="content">
		<view class="content1">
			<u--form labelPosition="left" :model="homeInfo" ref="uForm">
				<u-form-item label="公司/机构名称" prop="userInfo.name" borderBottom ref="item1">
					<u--input v-model="homeInfo.name" border="none" placeholder="输入公司名称"></u--input>
				</u-form-item>
				<view class="">
					<view class="">
						公司/机构简介
					</view>
					<u--textarea v-model="value2" placeholder="输入公司/机构简介,不超过1000个字" count maxlength="1000"></u--textarea>
				</view>
				<view class="">
					<view class="">
						业务范围
					</view>
					<u--textarea v-model="value2" placeholder="输入业务范围,不超过500个字" count maxlength="500"></u--textarea>
				</view>
				<u-form-item label="公司地址" prop="userInfo.name" borderBottom ref="item1">
					<u--input v-model="homeInfo.name" border="none" placeholder="输入地址"></u--input>
				</u-form-item>
				<u-form-item label="联系电话" prop="userInfo.name" borderBottom ref="item1">
					<u--input v-model="homeInfo.name" border="none" placeholder="输入地址"></u--input>
				</u-form-item>
				<u-form-item label="传真" prop="userInfo.name" borderBottom ref="item1">
					<u--input v-model="homeInfo.name" border="none" placeholder="输入地址"></u--input>
				</u-form-item>
				<u-form-item label="邮箱" prop="userInfo.name" borderBottom ref="item1">
					<u--input v-model="homeInfo.name" border="none" placeholder="输入地址"></u--input>
				</u-form-item>
				<view class="">
					<view class="">
						企业风采
					</view>
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
						:maxCount="10"></u-upload>
				</view>
			</u--form>
		</view>
		<view class="bottom">
			<button>提交</button>
		</view>
	</view>
</template>

<script>
		import uploadImage from '@/components/ossutil/uploadFile.js';
	export default {
		data() {
			return {
				fileList1: [],
				value2:'',
				homeInfo:{
					name:''
				}
			};
		},
		methods:{
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
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
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			uploadFilePromise(url) {
				return new Promise((resolve, reject) => {
					// uploadImage(res.tempFilePaths[0], 'cardImages/',
					// 				result => {
					// 					// this.trainImg = result
					// 					uni.hideLoading();
					// 				}
					// 			)
					// let a = uni.uploadFile({
					// 	url: 'http://192.168.2.21:7001/upload', // 仅为示例，非真实的接口地址
					// 	filePath: url,
					// 	name: 'file',
					// 	formData: {
					// 		user: 'test'
					// 	},
					// 	success: (res) => {
					// 		setTimeout(() => {
					// 			resolve(res.data.data)
					// 		}, 1000)
					// 	}
					// });
				})
			},
		}
	}
</script>

<style lang="scss">

</style>
