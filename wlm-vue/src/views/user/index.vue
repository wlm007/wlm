<template>
  <div class="user__index">
    <div class="user__index__form">
      <el-form ref="userIndexForm" :model="userSearchForm" :inline="true">
        <el-form-item label="姓名">
          <el-input v-model="userSearchForm.username" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
          <el-button type="primary" @click="showDialog('isAdd')">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="user__index__table">
      <el-table :data="data" border stripe height="650">
        <el-table-column prop="username" label="用户姓名" align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
        <el-table-column prop="age" label="年龄" align="center"></el-table-column>
        <el-table-column prop="deptName" label="部门" align="center"></el-table-column>
        <el-table-column prop="roleName" label="角色" align="center"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="toDetailOrEdit(scope.row, 'isView')" type="text" size="small">查看</el-button>
            <el-button type="text" size="small" @click="toDetailOrEdit(scope.row, 'isEdit')">编辑</el-button>
            <el-button type="text" size="small" @click="deleteUser(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="600px" :before-close="closeDialog">
      <div class="dialog__form">
        <el-form ref="dialogForm" :model="addUserForm" :disabled="showType === 'isView'">
          <el-form-item label="用户名称" label-width="120px" prop="username">
            <el-input v-model="addUserForm.username"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" label-width="120px" prop="email">
            <el-input v-model="addUserForm.email"></el-input>
          </el-form-item>
          <el-form-item label="年龄" label-width="120px" prop="age">
            <el-input v-model="addUserForm.age"></el-input>
          </el-form-item>
          <el-form-item label="部门" label-width="120px" prop="deptNo">
            <el-input v-model="addUserForm.deptNo"></el-input>
          </el-form-item>
          <el-form-item label="角色" label-width="120px" prop="roleNo">
            <el-input v-model="addUserForm.roleNo"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
        <el-button v-if="showType === 'isView'" type="primary" @click="closeDialog">确 定</el-button>
        <el-button v-else type="primary" @click="submit">提 交</el-button>
      </div>
    </el-dialog>
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
  </div>

</template>

<script>
import api from '@/webapi'

export default{
  data () {
    return {
      data: [],
      userSearchForm: {
        username: ''
      },
      showType: '',
      dialogTitle: '新增用户',
      dialogFormVisible: false,
      addUserForm: {
        id: 0,
        username: '',
        email: '',
        age: '',
        deptNo: '',
        roleNo: ''
      },
      total: 0,
      pageNo: 1,
      pageSize: 10
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    async getList () {
      const res = await api.sysUser.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        username: this.userSearchForm.username
      }, { dismissLoading: false })
      if (res.data.code === 200) {
        this.data = res.data.data.data
        this.total = res.data.data.total
      }
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
    toDetailOrEdit (row, showType) {
      this.showDialog(showType)
      // form 表单的赋值需要在弹框加载后，不然resetFields方法不生效
      this.$nextTick(() => {
        this.addUserForm.id = row.id
        this.addUserForm.username = row.username
        this.addUserForm.age = row.age
        this.addUserForm.email = row.email
        this.addUserForm.deptNo = row.deptNo
        this.addUserForm.roleNo = row.roleNo
      })
    },
    showDialog (showType) {
      switch (showType) {
        case 'isAdd' : this.dialogTitle = '新增用户信息'; break
        case 'isEdit' : this.dialogTitle = '编辑用户信息'; break
        case 'isView' : this.dialogTitle = '查看用户信息'; break
      }
      this.showType = showType
      this.dialogFormVisible = true
    },
    closeDialog () {
      this.$refs.dialogForm.resetFields()
      this.dialogFormVisible = false
    },
    async submit () {
      let res
      if (this.showType === 'isAdd') {
        res = await api.sysUser.register(this.addUserForm, { dismissLoading: false })
      } else if (this.showType === 'isEdit') {
        res = await api.sysUser.update(this.addUserForm, { dismissLoading: false })
      }
      if (res && res.data.code === 200) {
        this.$message.success('操作成功')
        this.closeDialog()
        this.getList()
      }
    },
    async deleteUser (row) {
      this.$alert('确定要删除改用户吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.sysUser.deleteUser(row.id)
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

<style scoped>
.user__index {
  padding: 30px;
  height: 96%;
  width: 96%;
  overflow: hidden;
}
.user__index__table {
  height: calc(100% - 100px);
}
.dialog__form {
  width: 500px;
  margin: auto;
}
</style>
