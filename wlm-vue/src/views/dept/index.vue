<template>
  <div class="dept__index">
    <div class="dept__tree">
      <el-input placeholder="请输入部门名称" v-model="deptFilterText"></el-input>
      <el-tree
        class="dept__tree__show"
        ref="deptTree"
        :data="treeData"
        :props="defaultProps"
        node-key="deptNo"
        :default-expanded-keys="defaultExpandedKeys"
        :default-checked-keys="defaultCheckedKeys"
        :current-node-key="currentNodeKey"
        :highlight-current="true"
        :filter-node-method="deptFilterNode"
        :render-content="treeRenderContent"
        accordion
        @node-click="handleNodeClick">
      </el-tree>
    </div>
    <div class="dept__user__show">
      <div class="dept__index__form">
        <el-form ref="userSearchForm" :model="userSearchForm" :inline="true">
          <el-form-item label="用户姓名">
            <el-input v-model="userSearchForm.username" placeholder="请输入用户姓名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getUserList">查询</el-button>
            <el-button type="primary" @click="showUserDialog('isAdd')">新增</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="dept__index__table">
        <el-table :data="userTableData" border stripe height="650">
          <el-table-column prop="username" label="用户姓名" align="center"></el-table-column>
          <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
          <el-table-column prop="age" label="年龄" align="center"></el-table-column>
          <el-table-column prop="deptName" label="部门" align="center"></el-table-column>
          <el-table-column prop="roleName" label="角色" align="center"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="toUserDetailOrEdit(scope.row, 'isView')" type="text" size="small">查看</el-button>
              <el-button type="text" size="small" @click="toUserDetailOrEdit(scope.row, 'isEdit')">编辑</el-button>
              <el-button type="text" size="small" @click="deleteUser(scope.row)">删除</el-button>
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
    </div>
    <el-dialog :title="userDialogTitle" :visible.sync="userDialogFormVisible" width="600px" :before-close="closeUserDialog">
      <div class="dialog__form">
        <el-form ref="userDialogForm" :model="addUserForm">
          <el-form-item label="用户名称：" label-width="120px" prop="username">
            <span v-if="userShowType === 'isView'">{{ addUserForm.username }}</span>
            <el-input v-else v-model="addUserForm.username"></el-input>
          </el-form-item>
          <el-form-item label="邮箱：" label-width="120px" prop="email">
            <span v-if="userShowType === 'isView'">{{ addUserForm.email }}</span>
            <el-input v-else v-model="addUserForm.email"></el-input>
          </el-form-item>
          <el-form-item label="年龄：" label-width="120px" prop="age">
            <span v-if="userShowType === 'isView'">{{ addUserForm.age }}</span>
            <el-input v-else v-model="addUserForm.age"></el-input>
          </el-form-item>
          <el-form-item label="部门：" label-width="120px" prop="deptNo">
            <span>{{ addUserForm.deptName }}</span>
          </el-form-item>
          <el-form-item label="角色：" label-width="120px" prop="roleNo">
            <span v-if="userShowType === 'isView'">{{ addUserForm.roleName }}</span>
            <div v-else class="select_role_show">
              <span>{{ addUserForm.roleName }}</span>
              <el-button type="primary" @click="roleVisible = true">选择角色</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeUserDialog">取 消</el-button>
        <el-button v-if="userShowType === 'isView'" type="primary" @click="closeUserDialog">确 定</el-button>
        <el-button v-else type="primary" @click="userSubmit">保 存</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="deptDialogTitle" :visible.sync="deptDialogFormVisible" width="600px" :before-close="closeDeptDialog">
      <div class="dialog__form">
        <el-form ref="deptDialogForm" :model="addDeptForm">
          <el-form-item label="上级部门：" label-width="120px" prop="parentDeptName">
            <span>{{addDeptForm.parentDeptName ? addDeptForm.parentDeptName : '无'}}</span>
          </el-form-item>
          <el-form-item label="部门名称：" label-width="120px" prop="username">
            <span v-if="deptShowType === 'isView'">{{ addDeptForm.deptName }}</span>
            <el-input v-else v-model="addDeptForm.deptName"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDeptDialog">取 消</el-button>
        <el-button v-if="deptShowType === 'isView'" type="primary" @click="closeDeptDialog">确 定</el-button>
        <el-button v-else type="primary" @click="deptSubmit">保 存</el-button>
      </div>
    </el-dialog>
    <select-role :visible.sync="roleVisible" title="请选择角色" :isSigle="true" @confirm="handleRoleSelect"></select-role>
  </div>

</template>

<script>
import api from '@/webapi'

export default{
  components: {
    selectRole: () => ({ component: import('@/views/role/selectRoleDialog.vue') })
  },
  data () {
    return {
      roleVisible: false,
      deptFilterText: '',
      treeData: [],
      currentNodeKey: 'root',
      defaultExpandedKeys: ['root'],
      defaultCheckedKeys: ['root'],
      defaultProps: {
        children: 'children',
        label: 'deptName'
      },
      deptDetail: {},
      userTableData: [],
      userSearchForm: {
        username: ''
      },
      userShowType: '',
      userDialogTitle: '新增用户',
      userDialogFormVisible: false,
      addUserForm: {
        id: 0,
        username: '',
        email: '',
        age: '',
        deptNo: '',
        deptName: '',
        roleNo: '',
        roleName: ''
      },
      deptShowType: '',
      deptDialogTitle: '新增部门',
      deptDialogFormVisible: false,
      addDeptForm: {
        id: 0,
        deptNo: '',
        deptName: '',
        parentNo: '',
        parentDeptName: ''
      },
      total: 0,
      pageNo: 1,
      pageSize: 10
    }
  },
  watch: {
    deptFilterText (val) {
      this.$refs.deptTree.filter(val)
    }
  },
  mounted () {
    this.getDeptTree()
  },
  methods: {
    treeRenderContent (h, { node, data, store }) {
      return (
        <span style="flex: 1;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    font-size: 14px;
                    padding-right: 8px;">
          <span>{node.label}</span>
          <span>
            <el-button type="text" size="mini" on-click={() => { this.showDeptDialog('isAdd', data) }}>新增</el-button>
            <el-button type="text" size="mini" on-click={() => { this.showDeptDialog('isEdit', data) }}>编辑</el-button>
            <el-button type="text" size="mini" on-click={() => { this.deleteDept(data) }}>删除</el-button>
          </span>
        </span>)
    },
    deptFilterNode (value, data) {
      if (!value) return true
      return data.deptName.indexOf(value) !== -1
    },
    deleteDept (row) {
      this.$confirm(`确定要删除部门：${row.deptName} ？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (row.deptNo === 'root') {
          this.$message.error('根部门不允许删除')
          return
        }
        api.sysDept.delete(row.id)
          .then(res => {
            if (res.data.code === 200) {
              this.$message.success('操作成功')
              this.getDeptTree()
              this.resetTreeNode('root')
            }
          })
          .catch(() => {})
      }).catch(() => {})
    },
    resetTreeNode (deptNo) {
      this.currentNodeKey = deptNo
      this.defaultExpandedKeys = [deptNo]
      this.defaultCheckedKeys = [deptNo]
    },
    handleNodeClick (data) {
      this.deptDetail = data
      this.resetTreeNode(data.deptNo)
      this.getUserList()
    },
    handleRoleSelect (data) {
      this.$nextTick(() => {
        this.addUserForm.roleNo = data.roleNo
        this.addUserForm.roleName = data.roleName
      })
    },
    async getDeptTree () {
      const res = await api.sysDept.treeList()
      if (res.data.code === 200) {
        this.treeData = res.data.data
        this.deptDetail = res.data.data[0]
        this.getUserList()
      }
    },
    async getUserList () {
      const res = await api.sysUser.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        deptNo: this.deptDetail.deptNo,
        username: this.userSearchForm.username
      }, { dismissLoading: false })
      if (res.data.code === 200) {
        this.userTableData = res.data.data.data
        this.total = res.data.data.total
      }
    },
    handleSizeChange (val) {
      this.pageSize = val
      if (this.pageNo === 1) {
        this.getUserList()
      } else {
        this.handleCurrentChange(1)
      }
    },
    handleCurrentChange (val) {
      this.pageNo = val
      this.getUserList()
    },
    toUserDetailOrEdit (row, userShowType) {
      this.showUserDialog(userShowType)
      // form 表单的赋值需要在弹框加载后，不然resetFields方法不生效
      this.$nextTick(() => {
        this.addUserForm.id = row.id
        this.addUserForm.username = row.username
        this.addUserForm.age = row.age
        this.addUserForm.email = row.email
        this.addUserForm.deptNo = row.deptNo
        this.addUserForm.deptName = row.deptName
        this.addUserForm.roleNo = row.roleNo
        this.addUserForm.roleName = row.roleName
      })
    },
    showUserDialog (userShowType) {
      switch (userShowType) {
        case 'isAdd' :
          this.userDialogTitle = '新增用户信息'
          this.addUserForm.deptNo = this.deptDetail.deptNo
          this.addUserForm.deptName = this.deptDetail.deptName
          break
        case 'isEdit' : this.userDialogTitle = '编辑用户信息'; break
        case 'isView' : this.userDialogTitle = '查看用户信息'; break
      }
      this.userShowType = userShowType
      this.userDialogFormVisible = true
    },
    closeUserDialog () {
      this.addUserForm.roleNo = ''
      this.addUserForm.roleName = ''
      this.$refs.userDialogForm.resetFields()
      this.userDialogFormVisible = false
    },
    showDeptDialog (deptShowType, data) {
      this.resetTreeNode(data.deptNo)
      switch (deptShowType) {
        case 'isAdd' : this.deptDialogTitle = '新增子部门'; break
        case 'isEdit' : this.deptDialogTitle = '编辑部门信息'; break
      }
      this.deptShowType = deptShowType
      this.deptDialogFormVisible = true
      this.$nextTick(() => {
        if (deptShowType === 'isAdd') {
          this.addDeptForm.id = 0
          this.addDeptForm.deptName = ''
          this.addDeptForm.parentNo = data.deptNo
          this.addDeptForm.parentDeptName = data.deptName
        } else {
          this.addDeptForm.id = data.id
          this.addDeptForm.deptName = data.deptName
          this.addDeptForm.parentNo = data.parentNo
          this.addDeptForm.parentDeptName = data.parentDeptName
        }
      })
    },
    closeDeptDialog () {
      this.$refs.deptDialogForm.resetFields()
      this.deptDialogFormVisible = false
    },
    async deptSubmit () {
      const res = await api.sysDept.save(this.addDeptForm, { dismissLoading: false })
      if (res && res.data.code === 200) {
        this.$message.success('操作成功')
        this.closeDeptDialog()
        this.getDeptTree()
      }
    },
    async userSubmit () {
      let res
      if (this.userShowType === 'isAdd') {
        res = await api.sysUser.register(this.addUserForm, { dismissLoading: false })
      } else if (this.userShowType === 'isEdit') {
        res = await api.sysUser.update(this.addUserForm, { dismissLoading: false })
      }
      if (res && res.data.code === 200) {
        this.$message.success('操作成功')
        this.closeUserDialog()
        this.getUserList()
      }
    },
    async deleteUser (row) {
      this.$confirm(`确定要删除用户：${row.username} ？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.sysUser.deleteUser(row.id)
          .then(res => {
            if (res.data.code === 200) {
              this.$message.success('操作成功')
              this.getUserList()
            }
          })
          .catch(() => {})
      }).catch(() => {})
    }
  }
}
</script>

<style scoped>
.dept__index {
  height: 100%;
  width: 100%;
  overflow: hidden;
  position: relative;
}
.dept__index__table {
  height: calc(100% - 80px);
}
.dialog__form {
  width: 500px;
  margin: auto;
}
.dept__tree {
  width: 29%;
  height: 100%;
  display: inline-block;
  position: absolute;
  top: 0px;
  left: 0px;
}
.dept__tree__show {
  margin-top: 20px;
}
.dept__user__show {
  width: 69%;
  height: 100%;
  display: inline-block;
  position: absolute;
  top: 0px;
  right: 0px;
}
.footer_pagination {
  position: absolute;
  bottom: 0px;
  width: 100%;
}
.select_role_show {
  margin: 0 30px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
