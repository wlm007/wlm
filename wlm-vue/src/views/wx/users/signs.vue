<template>
  <div class="user_sign_index">
    <div class="user_sign_index_form">
      <el-form ref="indexForm" :model="searchForm" :inline="true">
        <el-form-item>
          <el-button type="primary" @click="getList()">刷新</el-button>
          <el-button type="primary" @click="toAdd()">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="user_sign_index_table">
      <el-table
        :data="list" border stripe height="670"
        >
        <el-table-column prop="id" label="ID" align="center"></el-table-column>
        <el-table-column prop="name" label="标签名称" align="center"></el-table-column>
        <el-table-column prop="count" label="此标签下用户数" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="toEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="deleteSign(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="footer_pagination">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-size="pageSize"
        :page-sizes="[5, 10, 30, 50, 100]"
        :current-page="pageNo"
      >
      </el-pagination>
    </div>
    <el-dialog title="添加标签" :visible.sync="dialogFormVisible" width="600px" :before-close="closeDialog">
      <div class="dialog__form">
        <el-form ref="dialogForm" :model="addForm">
          <el-form-item label="标签名称" label-width="120px" prop="menuName">
            <el-input v-model="addForm.name"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="submit">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import api from '@/webapi'
export default {
  data () {
    return {
      searchForm: {},
      list: [],
      pageNo: 1,
      pageSize: 10,
      total: 0,
      addForm: {
        id: 0,
        name: ''
      },
      dialogFormVisible: false
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    async getList () {
      const res = await api.wxUsers.getUserSignlist(this.pageNo, this.pageSize)
      if (res.data.code === 200) {
        this.list = res.data.data.data
        this.total = res.data.data.total
      }
    },
    closeDialog () {
      this.dialogFormVisible = false
      this.addForm.name = ''
      this.addForm.id = 0
    },
    handleSizeChange (val) {
      this.pageSize = val
      if (this.pageNo === 1) {
        this.getList()
      } else {
        this.handleCurrentChange(1)
      }
    },
    handleCurrentChange (val) {
      this.pageNo = val
      this.getList()
    },
    toAdd () {
      this.$prompt('请输入标签名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: '',
        inputErrorMessage: '请输入标签名称'
      }).then(({ value }) => {
        api.wxUsers.addUserSign(value).then(res => {
          if (res.data.code === 200) {
            this.$message.success('操作成功')
            this.getList()
          }
        })
      }).catch(() => {})
    },
    toEdit (row) {
      this.addForm.id = row.id
      this.addForm.name = row.name
      this.dialogFormVisible = true
    },
    async submit () {
      const res = await api.wxUsers.editUserSign(this.addForm.id, this.addForm.name)
      if (res.data.code === 200) {
        this.$message.success('操作成功')
        this.getList()
        this.closeDialog()
      }
    },
    deleteSign (row) {
      this.$confirm(`确定删除标签: ${row.name}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.wxUsers.deleteUserSign(row.id)
          .then(res => {
            if (res.data.code === 200) {
              this.$message.success('操作成功')
              this.getList()
            }
          })
          .catch(() => {})
      }).catch(() => {})
    }
  }
}
</script>
