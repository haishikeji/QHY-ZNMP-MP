import * as config from '../config'
const ossUploadUrl = 'https://taohaoliang.oss-cn-beijing.aliyuncs.com/';

const contactCustomerService = (item) => {
	console.log(item)
	if (!item) {
		item = '110'
	}
	console.log("联系客服")
	uni.makePhoneCall({
		phoneNumber: item,
		success: (res) => {
			console.log('调用成功!')
		},
		// 失败回调
		fail: (res) => {
			console.log('调用失败!')
			contactCustomerService(item)
		}
	});
}

const logout = () => {
	uni.removeStorage({
		key: 'userInfo'
	})
}
const formatLocation = (str) => {
	let area = {}
	let index11 = 0
	let index1 = str.indexOf("省")
	if (index1 == -1) {
		index11 = str.indexOf("自治区")
		if (index11 != -1) {
			area.Province = str.substring(0, index11 + 3)
		} else {
			area.Province = str.substring(0, 0)
		}
	} else {
		area.Province = str.substring(0, index1 + 1)
	}

	let index2 = str.indexOf("市")
	if (index11 == -1) {
		area.City = str.substring(index11 + 1, index2 + 1)
	} else {
		if (index11 == 0) {
			area.City = str.substring(index1 + 1, index2 + 1)
		} else {
			area.City = str.substring(index11 + 3, index2 + 1)
		}
	}

	let index3 = str.indexOf("区")
	if (index3 == -1) {
		index3 = str.indexOf("县")
		if (index3 == -1) {
			index3 = str.lastIndexOf("市")
			if (index3 == -1) {
				index3 = str.indexOf("镇")
				if (index3 == -1) {

				} else {
					area.Country = str.substring(index2 + 1, index3 + 1)

				}
			} else {
				area.Country = str.substring(index2 + 1, index3 + 1)
			}
		} else {
			area.Country = str.substring(index2 + 1, index3 + 1)
		}

	} else {
		area.Country = str.substring(index2 + 1, index3 + 1)
	}
	area.Village = str.substring(index3 + 1, str.length)
	return area;
}



export default {
logout,
contactCustomerService,
formatLocation
}
