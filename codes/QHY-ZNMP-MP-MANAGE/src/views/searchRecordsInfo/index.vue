<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="个人id" prop="commonId">
        <el-input
          v-model="queryParams.commonId"
          placeholder="请输入个人id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="搜索内容" prop="searchContent">
        <el-input
          v-model="queryParams.searchContent"
          placeholder="请输入搜索内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
        <el-form-item label="功能类型" prop="functionType">
        <el-select v-model="queryParams.functionType" placeholder="请选择功能类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="gmtCreate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.gmtCreate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="" prop="gmtUpdate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.gmtUpdate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="删除标识" prop="deleteFlag">
        <el-input
          v-model="queryParams.deleteFlag"
          placeholder="请输入删除标识"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-permission="['unimall:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-permission="['unimall:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-permission="['unimall:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-permission="['unimall:info:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="" align="center" prop="id" />
      <el-table-column label="个人id" align="center" prop="commonId" />
      <el-table-column label="搜索内容" align="center" prop="searchContent" />
      <el-table-column label="功能类型" align="center" prop="functionType" />
      <el-table-column label="" align="center" prop="gmtCreate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtCreate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="" align="center" prop="gmtUpdate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtUpdate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="删除标识" align="center" prop="deleteFlag" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-permission="['unimall:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-permission="['unimall:info:remove']"
          >删除</el-button>
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

    <!-- 添加或修改搜索记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="个人id" prop="commonId">
          <el-input v-model="form.commonId" placeholder="请输入个人id" />
        </el-form-item>
        <el-form-item label="搜索内容" prop="searchContent">
          <el-input v-model="form.searchContent" placeholder="请输入搜索内容" />
        </el-form-item>
        <el-form-item label="功能类型">
          <el-select v-model="form.functionType" placeholder="请选择功能类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="" prop="gmtCreate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.gmtCreate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="" prop="gmtUpdate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.gmtUpdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标识" prop="deleteFlag">
          <el-input v-model="form.deleteFlag" placeholder="请输入删除标识" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo, exportInfo } from "@/api/card/info";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 搜索记录表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        commonId: undefined,
        searchContent: undefined,
        functionType: undefined,
        gmtCreate: undefined,
        gmtUpdate: undefined,
        deleteFlag: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        gmtCreate: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],        gmtUpdate: [
          { required: true, message: "不能为空", trigger: "blur" }
        ],        deleteFlag: [
          { required: true, message: "删除标识不能为空", trigger: "blur" }
        ]      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询搜索记录列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.data.data.items
        this.total = response.data.data.count
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        commonId: undefined,
        searchContent: undefined,
        functionType: undefined,
        gmtCreate: undefined,
        gmtUpdate: undefined,
        deleteFlag: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加搜索记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids.join(',')
      getInfo(id).then(response => {
        this.form = response.data.data
        this.open = true;
        this.title = "修改搜索记录";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateInfo(this.form).then(response => {
              if (response.data) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addInfo(this.form).then(response => {
              if (response.data) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids.join(',')
      this.$confirm('是否确认删除搜索记录编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delInfo(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有搜索记录数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportInfo(queryParams);
        }).then(response => {
          this.download(response.data.data);
        }).catch(function() {});
    }
  }
};
</script>