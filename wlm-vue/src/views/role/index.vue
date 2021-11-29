<template>
  <div class="role__index">
    <div class="role__index__form">
      <el-form ref="roleIndexForm" :model="roleSearchForm" :inline="true">
        <el-form-item label="角色名称">
          <el-input v-model="roleSearchForm.roleName" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
          <el-button type="primary" @click="showDialog('isAdd')">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="role__index__table">
      <el-table :data="data" border stripe height="650">
        <el-table-column prop="id" label="ID" align="center"></el-table-column>
        <el-table-column prop="roleNo" label="角色编号" align="center"></el-table-column>
        <el-table-column prop="roleName" label="角色名称" align="center"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="toDetailOrEdit(scope.row, 'isView')" type="text" size="small">查看</el-button>
            <el-button type="text" size="small" @click="toDetailOrEdit(scope.row, 'isEdit')">编辑</el-button>
            <el-button type="text" size="small" @click="deleteRole(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="600px" :before-close="closeDialog">
      <div class="dialog__form">
        <el-form ref="dialogForm" :model="addRoleForm" :disabled="showType === 'isView'">
          <el-form-item label="角色编号" label-width="120px" prop="roleName">
            <el-input v-model="addRoleForm.roleNo"></el-input>
          </el-form-item>
          <el-form-item label="角色名称" label-width="120px" prop="email">
            <el-input v-model="addRoleForm.roleName"></el-input>
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
      roleSearchForm: {
        roleName: ''
      },
      showType: '',
      dialogTitle: '新增用户',
      dialogFormVisible: false,
      addRoleForm: {
        id: 0,
        roleNo: '',
        roleName: ''
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
      const res = await api.sysRole.list({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        roleName: this.roleSearchForm.roleName
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
        this.addRoleForm.id = row.id
        this.addRoleForm.roleNo = row.roleNo
        this.addRoleForm.roleName = row.roleName
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
      this.addRoleForm.id = 0
      this.addRoleForm.roleNo = ''
      this.addRoleForm.roleName = ''
      this.$refs.dialogForm.resetFields()
      this.dialogFormVisible = false
    },
    async submit () {
      const res = await api.sysRole.save(this.addRoleForm, { dismissLoading: false })
      if (res && res.data.code === 200) {
        this.$message.success('操作成功')
        this.closeDialog()
        this.getList()
      }
    },
    async deleteRole (row) {
      this.$confirm(`确定删除角色：${row.roleName}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.sysRole.delete(row.id)
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
.role__index {
  padding: 30px;
  height: 96%;
  width: 96%;
  overflow: hidden;
}
.role__index__table {
  height: calc(100% - 100px);
}
.dialog__form {
  width: 500px;
  margin: auto;
}
.select_dept_show {
  margin: 0 30px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
