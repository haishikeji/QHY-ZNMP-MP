<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="表名称" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="请输入表名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="表描述" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="请输入表描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-permission="['tool:gen:code']"
          :disabled="multiple"
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleGenTable"
        >生成</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-permission="['tool:gen:import']"
          type="info"
          icon="el-icon-upload"
          size="mini"
          @click="openImportTable"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-permission="['tool:gen:edit']"
          :disabled="single"
          type="success"
          icon="el-icon-edit"
          size="mini"
          @click="handleEditTable"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-permission="['tool:gen:remove']"
          :disabled="multiple"
          type="danger"
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="序号" align="center" prop="tableId" width="50px" />
      <el-table-column
        :show-overflow-tooltip="true"
        label="表名称"
        align="center"
        prop="tableName"
      />
      <el-table-column
        :show-overflow-tooltip="true"
        label="表描述"
        align="center"
        prop="tableComment"
      />
      <el-table-column
        :show-overflow-tooltip="true"
        label="实体"
        align="center"
        prop="className"
      />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="160" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-permission="['tool:gen:preview']"
            type="text"
            size="small"
            icon="el-icon-view"
            @click="handlePreview(scope.row)"
          >预览</el-button>
          <el-button
            v-permission="['tool:gen:edit']"
            type="text"
            size="small"
            icon="el-icon-edit"
            @click="handleEditTable(scope.row)"
          >编辑</el-button>
          <el-button
            v-permission="['tool:gen:remove']"
            type="text"
            size="small"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button
            v-permission="['tool:gen:code']"
            type="text"
            size="small"
            icon="el-icon-download"
            @click="handleGenTable(scope.row)"
          >生成代码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!-- 预览界面 -->
    <el-dialog :title="preview.title" :visible.sync="preview.open" width="80%" top="5vh" >
      <el-tabs v-model="preview.activeName">
        <el-tab-pane
          v-for="(value, key) in preview.data"
          :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :name="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :key="key"
        >
          <pre style="height:600px;overflow:auto;">{{ value }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <import-table ref="import" @ok="handleQuery" />
  </div>
</template>

<script>
import { listTable, previewTable, delTable } from '@/api/tool/gen'
import importTable from './importTable'
import { downLoadZip } from '@/utils/zipdownload'
export default {
  name: 'Gen',
  components: { importTable },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 唯一标识符
      uniqueId: '',
      // 选中数组
      ids: [],
      // 选中表数组
      tableNames: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 表数据
      tableList: [],
      // 日期范围
      dateRange: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      },
      // 预览参数
      preview: {
        open: false,
        title: '代码预览',
        data: {},
        activeName: 'domain.java'
      }
    }
  },
  created() {
    this.getList()
  },
  activated() {
    const time = this.$route.query.t
    if (time != null && time !== this.uniqueId) {
      this.uniqueId = time
      this.resetQuery()
    }
  },
  methods: {
    /** 查询表集合 */
    getList() {
      this.loading = true
      listTable(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.tableList = response.data.data.items
        this.total = response.data.data.count
        this.loading = false
      }
      )
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 生成代码操作 */
    handleGenTable(row) {
      const tableNames = row.tableName || this.tableNames.join(',')
      if (tableNames.length === 0) {
        this.msgError('请选择要生成的数据')
        return
      }
      downLoadZip('/common/batchGenCode?tables=' + tableNames, 'ruoyi')
    },
    /** 打开导入表弹窗 */
    openImportTable() {
      this.$refs.import.show()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 预览按钮 */
    handlePreview(row) {
      previewTable(row.tableId).then(response => {
        this.preview.data = response.data.data
        this.preview.open = true
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId)
      this.tableNames = selection.map(item => item.tableName)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleEditTable(row) {
      const tableId = row.tableId || this.ids[0]
      this.$router.push('/tools/gen/edit/' + tableId)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tableIds = row.tableId || this.ids.join(',')
      this.$confirm('是否确认删除表编号为"' + tableIds + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delTable(tableIds)
      }).then(() => {
        this.getList()
        this.$notify.success({
          title: '成功',
          message: '删除成功'
        })
      }).catch(function() {})
    }
  }
}
</script>
