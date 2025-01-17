
<template>
	<view class="content">
		
		<view class="cu-list menu-avatar">
			<view 
				class="cu-item" v-if='item.hide==0' :class="modalName=='move-box-'+ index?'move-cur':''" v-for="(item, index) in taskInfo" :key="index"
				@click="navToDetailPage(item)"
				 @touchstart="ListTouchStart" @touchmove="ListTouchMove" @touchend="ListTouchEnd" :data-target="'move-box-' + index"
			>
			<view class='wrap' >
				<view  style='align-items: center;' class="flex justify-between padding">
					<view class=" padding-xs">
						<view class="radius">
							<view v-if="item.taskStatus != 1 " class="text-gray">
								<view style='font-weight: 600;font-size: 16px;margin-bottom: 5px;'>{{item.taskType}}<text v-if='item.carNo'>({{item.carNo}})</text></view>
							</view>
							<view v-else-if="item.taskType == '出库任务'" class="text-red">
								<view style='font-weight: 600;font-size: 16px;margin-bottom: 5px;'>{{item.taskType}}<text v-if='item.carNo'>({{item.carNo}})</text></view>
							</view>
							<view v-else-if="item.taskType == '入库任务'" class="text-green">
								<view style='font-weight: 600;font-size: 16px;margin-bottom: 5px;'>{{item.taskType}}<text v-if='item.carNo'>({{item.carNo}})</text></view>
							</view>
							<view v-else>
								<view style='font-weight: 600;font-size: 16px;margin-bottom: 5px;'>{{item.taskType}}<text v-if='item.carNo'>({{item.carNo}})</text></view>
							</view>
							<view style='color:#ccc;'>{{item.customer}}</view>
							<view style='color:#ccc;'>{{item.gmtUpdate}}</view>
						</view>
					</view>
					<view v-if="item.taskStatus == 1" class="tag-green line-green">
						执行中
					</view>
					<view  v-if="item.taskStatus == 2" class="tag-gray line-gray">
						已完成
					</view>
					<view  v-if="item.taskStatus == 3" class="tag-red line-red">
						已改派
					</view>
				</view>
			</view>
			<view v-if="item.companyId != 2" class="move" v-show='item.show==true' @click.stop="hided(item,$event)">
				<view class="bg-red">删除</view>
			</view>
			</view>
			<view v-show="isLoadMore">
				<uni-load-more :status="loadStatus" ></uni-load-more>
			</view>
		</view>
		<u-modal v-model="inputShow" 
			:title="title"
			:mask-close-able="true"
			show-cancel-button="true"
			:confirm-text="confirmText"
			:cancel-text="cancelText"
			@confirm="confirm"
			@cancel="cancel"  >
			<view v-if="taskDetail.orderNo" class="cu-form-group">
				<view  class="title">销售单号</view>
				{{taskDetail.orderNo}}
			</view>
			<view v-else-if="taskDetail.orderNoBuy" class="cu-form-group">
				<view  class="title">采购单号</view>
				{{taskDetail.orderNoBuy}}
			</view>
			<view v-if="taskDetail.seller" class="cu-form-group">
				<view class="title">采购来源</view>
				{{taskDetail.seller}}
			</view>
			<view v-if="taskDetail.cost" class="cu-form-group">
				<view class="title">库存成本</view>
				{{taskDetail.cost}}
			</view>
			<view v-if="currentTask.taskType == '审核任务'&&currentTask.saleType" class="cu-form-group">
				<view class="title">销售类型</view>
				{{currentTask.saleType}}
			</view>
			<view v-if="currentTask.taskType == '审核任务' && currentTask.saleType != '移库'&&currentTask.buyer" class="cu-form-group">
				<view class="title">销售流向</view>
				{{currentTask.buyer}}
			</view>
			<!-- <view v-if="currentTask.taskType == '审核任务'&&taskDetail.contractPrice<taskDetail.price" class="cu-form-group">
				<view class="title">销售价格</view>
				<view class="text-red ">{{taskDetail.contractPrice}}</view>
			</view> -->
			<view v-if="taskDetail.contractPrice" class="cu-form-group">
				<view class="title">销售价格</view>
				{{taskDetail.contractPrice}}
			</view>
			<view v-if="taskDetail.level" class="cu-form-group">
				<view class="title">货品</view>
				{{taskDetail.level + taskDetail.goodsName}}
			</view>
			
			<view v-if="currentTask.address " class="cu-form-group">
				<view v-if='currentTask.tranType!=1' class="title">发运地</view>
				<view v-if='currentTask.tranType==1' class="title">发站起始地</view>
				<text>{{currentTask.address}}</text>
			</view>
			<view v-if="currentTask.startInPlace" class="cu-form-group">
				<view v-if='currentTask.tranType!=2' class="title">发站目的地</view>
				<view v-if='currentTask.tranType==2' class="title">发站港口</view>
				{{currentTask.startInPlace}}
			</view>
			<view v-if="currentTask.endInPlace" class="cu-form-group">
				<view v-if='currentTask.tranType!=2' class="title">到站起始地</view>
				<view v-if='currentTask.tranType==2' class="title">到站港口</view>
				{{currentTask.endInPlace}}
			</view>
			<view v-if="currentTask.endPlace" class="cu-form-group">
				<view v-if='currentTask.tranType!=1' class="title">目的地</view>
				<view v-if='currentTask.tranType==1' class="title">到站目的地</view>
				{{currentTask.endPlace}}
			</view>
			<view v-if="currentTask.netWeight" class="cu-form-group">
				<view class="title">净重</view>
				{{currentTask.netWeight}}
			</view>
			<view v-if="currentTask.onePrice" class="cu-form-group">
				<view class="title">单价</view>
				{{currentTask.onePrice}}
			</view>
			<view v-if="currentTask.waterContent" class="cu-form-group">
				<text class="tit">粮食指标</text>
				<view class="con-list">
					<button class='cu-btn bg-green shadow' @click="foodsLook" >查看</button>
				</view>
			</view>
			<view v-if="currentTask.waterContent"   class="cu-form-group">
				<view class="title">付款方式</view>
				<picker @change="PayTypeChange" :value="payTypeIndex" :range="payType">
					<view class="picker">
						{{payTypeIndex>-1?payType[payTypeIndex]:'请选择'}}
					</view>
				</picker>
			</view>	
			<view v-if="taskDetail.gmtUpdate" class="cu-form-group">
				<view class="title">申请时间</view>
				{{taskDetail.gmtUpdate}}
			</view>
			<view v-else class="cu-form-group">
				<view class="title">申请时间</view>
				{{currentTask.gmtUpdate}}
			</view>
			<view v-if="currentTask.taskType == '定价任务'" class="cu-form-group">
				<view class="title">{{priceType}}(元/吨)</view>
				<input v-model="inputPrice" style="margin:20upx;"  placeholder="请输入指导价" />
			</view>
			<view v-else-if="priceType&&currentTask.outPrice" class="cu-form-group">
				<view v-if='currentTask.tranType!=1&&currentTask.tranType!=2' class="title">{{priceType}}</view>
				<view v-if='currentTask.tranType==1' class="title">发站运价</view>
				<view v-if='currentTask.tranType==2' class="title">北方拖车运价</view>
				{{currentTask.outPrice}}
			</view>
			<view v-if="currentTask.tranType==1&&currentTask.inPrice||currentTask.tranType==2&&currentTask.inPrice" class="cu-form-group">
				<view v-if='currentTask.tranType==1' class="title">到站运价</view>
				<view v-if='currentTask.tranType==2' class="title">南方拖车运价</view>
				{{currentTask.inPrice}}
			</view>
		</u-modal>
		<u-modal v-model="inputShow2"
		    :mask-close-able="true"
			:title="title"
			show-cancel-button="true"
			:confirm-text="confirmText"
			:cancel-text="cancelText"
			@confirm="confirm"
			@cancel="cancel"  >
			
			<view v-if="paymentaudit.type == '采购'">
				<view v-if="paymentaudit.orderNoBuy" class="cu-form-group">
					<view  class="title">采购单号</view>
					{{paymentaudit.orderNoBuy}}
				</view>
				<view v-if="paymentaudit.seller" class="cu-form-group">
					<view  class="title">卖方</view>
					{{paymentaudit.seller}}
				</view>
				<view v-if="paymentaudit.contractNo" class="cu-form-group">
					<view  class="title">合同编号</view>
					{{paymentaudit.contractNo}}
				</view>
				<view v-if="paymentaudit.poundImg" class="cu-form-group">
					<view  class="title">磅单照片</view>
					<view @click="previewImg(paymentaudit.poundImg)" class="text-blue">查看</view>
				</view>
				<view v-if="paymentaudit.carNos" class="cu-form-group">
					<view  class="title">车牌号</view>
					{{paymentaudit.carNos}}
				</view>
				<view v-if="paymentaudit.buy == 0" class="cu-form-group">
					<view  class="title">成交价</view>
					{{paymentaudit.unitPrice}}
				</view>
				<view v-if="paymentaudit.buy > 0" class="cu-form-group">
					<view  class="title">平仓价</view>
					{{paymentaudit.unitPrice}}
				</view>
				<view v-if="paymentaudit.deduction" class="cu-form-group">
					<view  class="title">扣款</view>
					{{paymentaudit.deduction}}
				</view>
				<view v-if="paymentaudit.unloadingFee" class="cu-form-group">
					<view  class="title">卸车费</view>
					{{paymentaudit.unloadingFee}}
				</view>
				<view v-if="paymentaudit.invoiceMoney" class="cu-form-group">
					<view  class="title">发票费</view>
					{{paymentaudit.invoiceMoney}}
				</view>
				<view v-if="paymentaudit.basis" class="cu-form-group">
					<view  class="title">基差</view>
					{{paymentaudit.basis}}
				</view>
				<view v-if="paymentaudit.unitPrice1" class="cu-form-group">
					<view  class="title">付款单价</view>
					{{paymentaudit.unitPrice1}}
				</view>
				<view v-if="paymentaudit.netWeights" class="cu-form-group">
					<view  class="title">净重</view>
					{{paymentaudit.netWeights}}
				</view>
				<view v-if="paymentaudit.money" class="cu-form-group">
					<view  class="title">付款金额</view>
					{{paymentaudit.money}}
				</view>
			</view>
			<view v-else>
				<view v-if="paymentaudit.orderNo" class="cu-form-group">
					<view  class="title">销售单号</view>
					{{paymentaudit.orderNo}}
				</view>
				<view v-if="paymentaudit.buyer" class="cu-form-group">
					<view  class="title">买方</view>
					{{paymentaudit.buyer}}
				</view>
				<view v-if="paymentaudit.contractNo" class="cu-form-group">
					<view  class="title">合同编号</view>
					{{paymentaudit.contractNo}}
				</view>
				<view class="cu-form-group">
					<view  class="title">成交价</view>
					{{paymentaudit.unitPrice}}
				</view>
				<view v-if="paymentaudit.buy >0" class="cu-form-group">
					<view  class="title">平仓价</view>
					{{paymentaudit.unitPrice + paymentaudit.unitPrice1-paymentaudit.basis}}
				</view>
				<view v-if="paymentaudit.buy" class="cu-form-group">
					<view  class="title">平仓吨数</view>
					{{paymentaudit.buy}}
				</view>
				<view v-if="paymentaudit.unitPrice1" class="cu-form-group">
					<view  class="title">结算单价</view>
					{{paymentaudit.unitPrice1}}
				</view>
				<view v-if="paymentaudit.basis" class="cu-form-group">
					<view  class="title">平仓基差</view>
					{{paymentaudit.basis}}
				</view>
				<view v-if="paymentaudit.money" class="cu-form-group">
					<view  class="title">付款金额</view>
					{{paymentaudit.money}}
				</view>
			</view>
		</u-modal>
		<u-modal v-model="inputShow3"
			:mask-close-able="true"
			:title="title"
			show-cancel-button="true"
			:confirm-text="confirmText"
			:cancel-text="cancelText"
			@confirm="confirm"
			@cancel="cancel"  >
			<view v-if="paymentaudit.unitPrice" class="cu-form-group">
				<view  class="title">点价</view>
				{{paymentaudit.unitPrice}}
			</view>
			<view v-if="paymentaudit.basis" class="cu-form-group">
				<view  class="title">基差</view>
				{{paymentaudit.basis}}
			</view>
			
			<view class="cu-form-group">
				<view  class="title">发票</view>
				{{paymentaudit.invoiceFee}}
			</view>
			<view v-if="paymentaudit.unloadingFee" class="cu-form-group">
				<view  class="title">卸车费</view>
				{{paymentaudit.unloadingFee}}
			</view>
			<view v-if="paymentaudit.packingMoney" class="cu-form-group">
				<view  class="title">包装费</view>
				{{paymentaudit.packingMoney}}
			</view>
			<view v-if="paymentaudit.tradeContractNo" class="cu-form-group">
				<view  class="title">合同编号</view>
				{{paymentaudit.tradeContractNo}}
			</view>
			<view v-if="paymentaudit.storeName1" class="cu-form-group">
				<view  class="title">库点</view>
				{{paymentaudit.storeName1}}
			</view>
			<view v-if="paymentaudit.packing" class="cu-form-group">
				<view  class="title">包装方式</view>
				{{paymentaudit.packing}}
			</view>
			<view v-if="paymentaudit.invoiceType" class="cu-form-group">
				<view  class="title">发票类型</view>
				{{paymentaudit.invoiceType}}
			</view>
		</u-modal>
		<u-modal v-model="overbookingshow"
			:mask-close-able="true"
			:title="title"
			show-cancel-button="true"
			:confirm-text="confirmText"
			:cancel-text="cancelText"
			@confirm="confirm"
			@cancel="cancel"  >
			<view v-if="paymentaudit.customer" class="cu-form-group">
				<view  class="title">客户</view>
				{{paymentaudit.customer}}
			</view>
			<view v-if="paymentaudit.type" class="cu-form-group">
				<view  class="title">类型</view>
				{{paymentaudit.type}}
			</view>
			
			<view class="cu-form-group">
				<view  class="title">数量</view>
				{{paymentaudit.tradeCount}}
			</view>
			<view v-if="paymentaudit.title" class="cu-form-group">
				<view  class="title">库点</view>
				{{paymentaudit.title}}
			</view>
			<view v-if="paymentaudit.unitPrice" class="cu-form-group">
				<view  class="title">点价</view>
				{{paymentaudit.unitPrice}}
			</view>
			<view v-if="paymentaudit.basis" class="cu-form-group">
				<view  class="title">基差</view>
				{{paymentaudit.basis}}
			</view>
			<view v-if="paymentaudit.gmtCreate" class="cu-form-group">
				<view  class="title">时间</view>
				{{paymentaudit.gmtCreate}}
			</view>
		</u-modal>
		<!-- <drag-button
		 v-if="companyId != 2"
		:isDock="true"
		:existTabBar="true"
		:isIcon='false'
		text="放货"
		location="40"
		icon='cuIcon-deliver'
		@btnClick="fanghuo"
		/> -->
	</view>
</template>

<script>
	import dragButton from "@/components/drag-button/drag-button.vue";
	import {
		mapState
	} from 'vuex';
	export default {
		components: {
		    dragButton
		},
		data() {
			return {
				taskInfo: [],
				pages:1,//页数
				limit:20 ,//每次取条目数
				loadStatus:'loading',  //加载样式：more-加载前样式，loading-加载中样式，nomore-没有数据样式
				isLoadMore:false  ,//是否加载中
				inputPrice:'',
				inputShow: false,
				inputShow2: false,
				inputShow3: false,
				taskDetail:[],
				currentTask:[],
				delBtnWidth:200,
				payType: ['装车付款', '到货付款'],
				payTypeIndex: -1,
				title:'',
				priceType:'',
				confirmText:'',
				cancelText:'',
				fanghuoShow:'',
				payShow:false,
				fanghuoInfo:'',
				foodsValue:'',
				listTouchStart: 0,
				listTouchDirection: null,
				modalName: null,
				paymentaudit:[],
				companyId:1,
				overbookingshow:false
			};
		},
		computed: {
			...mapState(['hasLogin','userInfo'])
		},
		onShow() {
			this.loadData();
		},
		onLoad(options) {
			this.companyId = options.companyId
			console.log(options)
		},
		onPageScroll(e) {
		},
		//下拉刷新
		onPullDownRefresh() {
			this.pages = 1
			this.isLoadMore = false
			this.loadStatus = 'loading'
			this.loadData()
		},
		onReachBottom(){  //上拉触底函数
		  if(!this.isLoadMore){  //此处判断，上锁，防止重复请求
				this.isLoadMore=true
				this.pages += 1
				this.getIndexTaskData()
		  }
		},
		methods: {
			previewImg(urls){
				uni.previewImage({urls:[urls]})
			},
			hided(item,e){
				const that = this
				that.$api.request('task', 'hided', {
					taskId:item.id
				},failres => {
					that.$api.msg(failres.errmsg)
					that.isLoadMore=false
					that.loadStatus = 'nomore'
					// if(that.pages>1){that.pages-=1}
					uni.hideLoading()
				}).then(res => {
					item.hide=1
					uni.hideLoading()
				})
			},
			fanghuo() {
				uni.navigateTo({
					url: `/pages/user/task_detail_fh`
				})
			},
			//加载商品 ，带下拉刷新和上滑加载
			async loadData() {
				const that = this
				uni.showLoading({
					title: '正在加载',
						mask:true
				})
				
				that.$api.request('task', 'getTaskListInfo', {
					page: 1,
					limit:20
				},failres => {
					that.$api.msg(failres.errmsg)
					that.isLoadMore=false
					that.loadStatus = 'nomore'
					// if(that.pages>1){that.pages-=1}
					uni.hideLoading()
					uni.stopPullDownRefresh()
				}).then(res => {
					let data = res.data
					console.log(data)
					if(data.length > 0){
						for(var i=0;i<data.length;i++){
							data[i].leftStyle=0
							data[i].show=false
						}
						that.taskInfo = data
						that.isLoadMore=false
						that.pages = 1
						that.loadStatus = 'loading'
					}
					else{
						// if(that.pages>1){that.pages-=1}						
						that.isLoadMore=true
						that.loadStatus = 'nomore'
					}
					uni.hideLoading()
					uni.stopPullDownRefresh()
				})
			},
			//详情
			navToDetailPage(item) {
				this.currentTask = item
				console.log(this.currentTask)
				if(item.price){
					this.inputPrice = item.price
				}
				else{
					this.inputPrice = ""
				}
				if(item.taskType == "定价任务"){
					this.title = '指定销售价格'
					this.priceType = '销售指导价'
					this.confirmText = '确定'
					this.cancelText = '取消'
					uni.showLoading({
						title: '正在加载',
						mask:true
					})
					const that = this
					this.$api.request('task', 'getTaskDetailInfo', {
						orderNo: item.orderNo
					},failres => {
						that.$api.msg(failres.errmsg)
						uni.hideLoading()
					}).then(res => {
						that.taskDetail = res.data
						console.log(res.data)
						if(!that.taskDetail.orderNo){
							that.title = '指定采购价格'
							that.priceType = '采购指导价'
						}
						that.inputShow = true
						uni.hideLoading()
					})
				}
				else if(item.taskType == "审核任务"){
					if(item.memo=="库内自采"||item.memo=="外采(含运费)"){
						this.title=item.memo
					}
					else if(item.memo=="外采(不含运费)"&&item.sendCarNo!=null){
						this.title=item.memo
						this.foodsValue = "水分："+item.waterContent+"%，"+"容重："+item.bulkDensity+"，"+"焦糊粒："+item.burnt+"%，"+"霉变粒："+item.moldy+"%"
					}
					else{
					this.title = '审核运价'
					this.priceType = '申请运价(元/吨)'
					}
					if(item.saleType == "库内销售"){
						this.title = '申请审核'
						this.priceType = ''
					}
					else if(item.saleType == "装箱进港"){
						this.title = '申请审核'						
						this.priceType = ''
					}
					else if(item.saleType == "散船平仓"){
						this.title = '申请审核'						
						this.priceType = ''
					}
					if(this.currentTask.taskStatus == 1){
						this.confirmText = '同意'
						this.cancelText = '驳回'
					}
					else{
						this.confirmText = '确定'
						this.cancelText = '取消'
					}
					uni.showLoading({
						title: '正在加载',
						mask:true
					})
					const that = this
					this.$api.request('task', 'getTaskDetailInfo', {
						orderNo: item.orderNo,
						tranNo:item.tranNo
					},failres => {
						that.$api.msg(failres.errmsg)
						uni.hideLoading()
					}).then(res => {
						this.taskDetail = res.data
						console.log(this.taskDetail)
						this.inputShow = true
						uni.hideLoading()
					})
				}
				else if(item.taskType == "付款审核任务"){

					if(this.currentTask.taskStatus == 1){
						this.confirmText = '同意'
						this.cancelText = '驳回'
					}
					else{
						this.confirmText = '确定'
						this.cancelText = '取消'
					}
					uni.showLoading({
						title: '正在加载',
						mask:true
					})
					const that = this
					this.paymentaudit = item
					this.inputShow2 = true
					uni.hideLoading()
				}
				else if(item.taskType == "下单任务"){
					this.confirmText = '确定'
					this.cancelText = '取消'
					uni.showLoading({
						title: '正在加载',
						mask:true
					})
					const that = this
					this.paymentaudit = item
					this.overbookingshow = true
					uni.hideLoading()
				}
				else if(item.taskType == "交易审核任务"){	         
					var taskId = item.id
					var taskType = "交易审核任务"
					var taskStatus = item.taskStatus
					uni.navigateTo({
						url: `/pageA/pages/task_detail_jy?taskId=${taskId}&taskType=${taskType}&taskStatus=${taskStatus}`
					})
				}
				else if(item.taskType == "发运任务"){
					const that = this
					if(item.saleType == "移库" || item.saleType == "运送到厂"){
						
						//判断是否为火运任务
						if(!item.sendCarNo){
							var taskId = item.id
							var taskType = "发运任务"
							var taskStatus = item.taskStatus
							uni.navigateTo({
								url: `/pageA/pages/task?taskId=${taskId}&taskType=${taskType}&taskStatus=${taskStatus}`
							})
						}
						else{
							
							that.$api.request('tran', 'getCarInfoBySendCarNo', {
								sendCarNo:item.sendCarNo
							}, failres => {
								that.$api.msg(failres.errmsg)
								uni.hideLoading()
							}).then(res => {		
								console.log(res)
								if(res.data.status == 13){
									var sendCarNo = item.sendCarNo
									var taskStatus = item.taskStatus
									var taskId = item.id
									uni.navigateTo({
										url: `/pages/user/task_before?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}`
									})
								}
								else{
									var sendCarNo = item.sendCarNo
									var taskStatus = item.taskStatus
									var taskId = item.id
									var taskType = "发运任务"
									uni.navigateTo({
										url: `/pageA/pages/task_detail?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
									})
								}
								uni.hideLoading()
							})
						}
					}
					//散船平仓海运情况
					else if(item.saleType == "散船平仓"){
						var taskId = item.id								
						var taskStatus = item.taskStatus
						uni.navigateTo({
							url: `/pages/user/task_detail_pc?taskId=${taskId}&taskStatus=${taskStatus}`
						})
					}
					//装箱进港海运情况
					else if(item.saleType == "装箱进港"){
						var taskId = item.id								
						var taskStatus = item.taskStatus
						uni.navigateTo({
							url: `/pages/user/task_detail_zx?taskId=${taskId}&taskStatus=${taskStatus}`
						})
					}
					else if(item.saleType == "库内销售"){
						var taskId = item.id								
						var taskStatus = item.taskStatus
						uni.navigateTo({
							url: `/pages/user/task_detail_kn?taskId=${taskId}&taskStatus=${taskStatus}`
						})
					}
					else{
						var sendCarNo = item.sendCarNo
						var taskStatus = item.taskStatus
						var taskId = item.id
						var taskType = "发运任务"
						uni.navigateTo({
							url: `/pageA/pages/task_detail?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
						})
					}
					
				}
				else if(item.taskType == "交货任务"){
					const that = this
					var taskStatus = item.taskStatus
					var taskId = item.id
					uni.navigateTo({
						url: `/pages/user/task_detail_jh?taskId=${taskId}&taskStatus=${taskStatus}`
					})
				}
				else if(item.taskType == "发站完货"){
					const that = this
					var taskId = item.id
					var taskType = "发站完货"
					var taskStatus = item.taskStatus
					uni.navigateTo({
						url: `/pages/user/task_detail_hy?taskId=${taskId}&taskType=${taskType}&taskStatus=${taskStatus}`
					})
				}
				else if(item.taskType == "入库任务"){
					const that = this
					if(item.tranType==1){
						var sendCarNo = item.sendCarNo
						var taskStatus = item.taskStatus
						var taskId = item.id
						var taskType = "入库任务"
						uni.navigateTo({
							url: `/pages/user/task_detail_hyrk?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
						})
					}
					else if(item.memo=="外采(不含运费)"){
						var sendCarNo = item.sendCarNo
						var taskStatus = item.taskStatus
						var taskId = item.id
						var taskType = "入库任务"
						uni.navigateTo({
							url: `/pages/user/task_detail_wai?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
						})
					}
					else{
						var sendCarNo = item.sendCarNo
						var taskStatus = item.taskStatus
						var taskId = item.id
						var taskType = "入库任务"
						uni.navigateTo({
							url: `/pageA/pages/task_detail?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
						})
					}
				}
				else if(item.taskType == "到站入库"){
					console.log(item)
					const that = this
					var sendCarNo = item.sendCarNo
					var taskStatus = item.taskStatus
					var taskId = item.id
					var taskType = "入库任务"
					uni.navigateTo({
						url: `/pages/user/task_detail_hyrk?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
					})
				
				}
				else if(item.taskType == "自采任务"){
					var sendCarNo = item.sendCarNo
					var taskStatus = item.taskStatus
					var taskId = item.id
					var taskType = "自采任务"
					uni.navigateTo({
						url: `/pages/user/task_detail_buy?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
					})
				}
				else if(item.taskType == "到站下线"){
					console.log(item.taskType)
					var sendCarNo = item.sendCarNo
					var taskStatus = item.taskStatus
					var taskId = item.id
					var taskType = "到站下线"
					uni.navigateTo({
						url: `/pages/user/task_arrive?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
					})
				}
				else if(item.taskType == "外采任务"&&item.memo=="外采(含运费)"){
					var sendCarNo = item.sendCarNo
					var taskStatus = item.taskStatus
					var taskId = item.id
					var taskType = "自采任务"
					uni.navigateTo({
						url: `/pages/user/task_detail_buy?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
					})
				}
				else if(item.taskType == "外采任务"&&item.memo=="外采(不含运费)"){
					const that = this
					that.$api.request('tran', 'getCarInfoBySendCarNo', {
						sendCarNo:item.sendCarNo
					}, failres => {
						that.$api.msg(failres.errmsg)
						uni.hideLoading()
					}).then(res => {			
						if(res.data.status == 13){
							var sendCarNo = item.sendCarNo
							var taskStatus = item.taskStatus
							var taskId = item.id
							uni.navigateTo({
								url: `/pages/user/task_before?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}`
							})
						}
						else{
							var sendCarNo = item.sendCarNo
							var taskStatus = item.taskStatus
							var taskId = item.id
							var taskType = "外采任务"
							uni.navigateTo({
								url: `/pages/user/task_detail_buy?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
							})
						}
						uni.hideLoading()
					})
				}
				else if(item.taskType == "收货跟踪"&&item.memo!="客户自理"){
					var sendCarNo = item.sendCarNo
					var taskStatus = item.taskStatus
					var taskId = item.id
					var taskType = "收货跟踪"
					uni.navigateTo({
						url: `/pageA/pages/task_detail?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
					})
				}
				else if(item.taskType == "收货跟踪"&&item.memo=="客户自理"){
					var taskId = item.id
					var taskType = "收货跟踪"
					var taskStatus = item.taskStatus
					uni.navigateTo({
						url: `/pageA/pages/task_detail_ys?taskId=${taskId}&taskType=${taskType}&taskStatus=${taskStatus}&`
					})
				}
				else if(item.taskType == "出库任务"){
					console.log(1111)
					var sendCarNo = item.sendCarNo
					var taskStatus = item.taskStatus
					var taskId = item.id
					var taskType = "出库任务"
					uni.navigateTo({
						url: `/pageA/pages/task_detail_ck?sendCarNo=${sendCarNo}&taskStatus=${taskStatus}&taskId=${taskId}&taskType=${taskType}`
					})
				}
				uni.hideLoading()
			},
			// ListTouch触摸开始
						ListTouchStart(e) {
							this.listTouchStart = e.touches[0].pageX
						},
			
						// ListTouch计算方向
						ListTouchMove(e) {
							this.listTouchDirection = e.touches[0].pageX - this.listTouchStart > 0 ? 'right' : 'left'
						},
			
						// ListTouch计算滚动
						ListTouchEnd(e) {
							if (this.listTouchDirection == 'left') {
								this.modalName = e.currentTarget.dataset.target
							} else {
								this.modalName = null
							}
							this.listTouchDirection = null
						},
			foodsLook(){
				uni.showModal({
					title: '粮食指标',
					content: this.foodsValue,
					showCancel: false,
					confirmText: '确定',
					success: (e) => {
                          
					},
					fail: () => {},
					complete: () => {}
				})
			},
			PayTypeChange(e) {
				this.payTypeIndex = e.detail.value
			},
			cancel() {
				if(this.currentTask.taskType == "审核任务"||this.currentTask.taskType == "付款审核任务"||this.currentTask.taskType == "交易审核任务"){
					if(this.currentTask.taskStatus == 1){
						const that = this
						that.$api.request('task', 'doTask', {
							id: that.currentTask.id,
							reply:"驳回"
						}).then(res => {
							that.$api.msg('驳回成功')
							setTimeout(()=>{
								that.inputShow = false
								that.inputShow2 = false
								that.inputShow3 = false
								},1000);
							that.loadData()
						})
					}
					else{
						this.inputShow = false
					}
				}else if(this.currentTask.taskType == "下单任务"){
					this.overbookingshow = false
				}
				else{
					this.inputShow = false
				}
			},
			confirm() {
				uni.showLoading({
					title: '正在加载',
					mask:true
				})
				var paymentMethod;
				if(this.payTypeIndex == 0){
					paymentMethod = "装车付款"
				}
				else if(this.payTypeIndex == 1){
					paymentMethod = "到货付款"
				}
				if(this.currentTask.taskType == "定价任务"){
					const that = this
					if (!that.inputPrice) {
						that.$api.msg('输入价格不能为空')
						that.inputShow = true
						return
					}
					that.$api.request('task', 'doTask', {
						id: that.currentTask.id,
						price:that.inputPrice
					}).then(res => {
						that.$api.msg('定价任务已完成')
						that.inputShow = false
						uni.hideLoading()
						that.loadData()
					})
				}
				else if(this.currentTask.taskType == "审核任务"||this.currentTask.taskType == "付款审核任务"){
					if(this.payTypeIndex == -1&&this.currentTask.waterContent!=null){
						this.$api.msg('请选择付款方式');
						this.inputShow = true;
						uni.hideLoading()
						return
					}
					if(this.currentTask.taskStatus == 1){
						const that = this
						that.$api.request('task', 'doTask', {
							id: that.currentTask.id,
							payType:!paymentMethod?'':paymentMethod,
							reply:"同意"
						}).then(res => {
							uni.hideLoading()
							that.$api.msg('审核成功')
							that.loadData()
							setTimeout(()=>{
								that.inputShow = false 
								that.inputShow2 = false
								},1000);
							
						})
					}
					else{
						this.inputShow = false
					}
				}	
				else if(this.currentTask.taskType == "交易审核任务"){
						const that = this
						that.$api.request('task', 'doTask', {
							id: that.currentTask.id,
							payType:!paymentMethod?'':paymentMethod,
							reply:"同意"
						}).then(res => {
							uni.hideLoading()
							that.$api.msg('审核成功')
							that.loadData()
							setTimeout(()=>{
								that.inputShow3 = false 
								},1000);
							
						})
				}else if(this.currentTask.taskType == "下单任务"){
					const that = this
					that.$api.request('task', 'doTask', {
						id: that.currentTask.id,
					}).then(res => {
						that.$api.msg('确定成功')
						console.log(that)
						setTimeout(()=>{
							that.overbookingshow = false
							},1000);
						that.loadData()
					})
				}
			},
			getIndexTaskData(){
				const that = this
			    var pages=that.pages
			    var limit=that.limit
			    uni.showLoading({
			    	title: '正在加载',
					mask:true
			    })
			    that.$api.request('task', 'getTaskListInfo', {
					page: pages,
					limit:limit
				},failres => {
			    	that.$api.msg(failres.errmsg)
					that.isLoadMore=false
					that.loadStatus = 'nomore'
					if(that.pages>1){that.pages-=1}
			    	uni.hideLoading()
			    }).then(res => {
			    	let data = res.data
			    	//采购信息
			    	if(data.length > 0){
			    		that.taskInfo = that.taskInfo.concat(data)
						that.isLoadMore=false
			    	}
					else{
						if(that.pages>1){that.pages-=1}						
						that.isLoadMore=true
						that.loadStatus = 'nomore'
					}
			    	uni.hideLoading()
			    })
			}
		},
	}
</script>

<style lang="scss" scoped>
	.wrap{
		width:100%;
	}
	.tag-green{
		border: 1px solid #39b54a;
		padding: 4px 13px;
		border-radius: 5px;
		height:30px;
	}
	.tag-gray{
		border: 1px solid #aaaaaa;
		padding: 4px 13px;
		border-radius: 5px;
		height:30px;
	}
	.tag-red{
		border: 1px solid #e54d42;
		padding: 4px 13px;
		border-radius: 5px;
		height:30px;
	}
	
	.cu-list>.cu-item .move{
		width: 110px;
	}
	.del{
		position: absolute;
		    top: 50%;
		    -webkit-transform: translateY(-50%);
		    transform: translateY(-50%);
		    right: -197rpx;
		    width: 141rpx;
		    height: 140rpx;
		    line-height:140rpx;
		    font-weight: 500;
		    font-size: 30rpx;
		    text-align: center;
		    color: #FFFFFF;
		    background: #FF1C1C;
	}
	.navbar {
		position: fixed;
		left: 0;
		top: var(--window-top);
		display: flex;
		width: 100%;
		height: 80upx;
		background: #fff;
		box-shadow: 0 2upx 10upx rgba(0, 0, 0, .06);
		z-index: 10;
	
		.nav-item {
			flex: 1;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			font-size: 30upx;
			color: $font-color-dark;
			position: relative;
			&.current {
				color: $base-color;
				&:after {
					content: '';
					position: absolute;
					left: 50%;
					bottom: 0;
					transform: translateX(-50%);
					width: 120upx;
					height: 0;
					border-bottom: 4upx solid $base-color;
				}
			}
		}
	
		.p-box {
			display: flex;
			flex-direction: column;
	
			.yticon {
				display: flex;
				align-items: center;
				justify-content: center;
				width: 30upx;
				height: 14upx;
				line-height: 1;
				margin-left: 4upx;
				font-size: 26upx;
				color: #888;
	
				&.active {
					color: $base-color;
				}
			}
	
			.xia {
				transform: scaleY(-1);
			}
		}
	
		.cate-item {
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			width: 80upx;
			position: relative;
			font-size: 44upx;
	
			&:after {
				content: '';
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				border-left: 1px solid #ddd;
				width: 0;
				height: 36upx;
			}
		}
	}
	
	/* 分类 */
	.cate-mask {
		position: fixed;
		left: 0;
		top: var(--window-top);
		bottom: 0;
		width: 100%;
		background: rgba(0, 0, 0, 0);
		z-index: 95;
		transition: .3s;
	
		.cate-content {
			width: 630upx;
			height: 100%;
			background: #fff;
			float: right;
			transform: translateX(100%);
			transition: .3s;
		}
	
		&.none {
			display: none;
		}
	
		&.show {
			background: rgba(0, 0, 0, .4);
	
			.cate-content {
				transform: translateX(0);
			}
		}
	}
	
	.cate-list {
		display: flex;
		flex-direction: column;
		height: 100%;
		.cate-item {
			display: flex;
			align-items: center;
			height: 90upx;
			padding-left: 30upx;
			font-size: 28upx;
			color: #555;
			position: relative;
		}
		.two {
			height: 64upx;
			color: #303133;
			font-size: 30upx;
			background: #f8f8f8;
		}
		.active {
			color: $base-color;
		}
	}
	.guess-section .guess-item{
		border-bottom:none;
	}
	
</style>
