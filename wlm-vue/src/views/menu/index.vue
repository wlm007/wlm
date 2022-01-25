<template>
  <div class="menu__index">
    <div class="menu__index__form">
      <el-form ref="IndexForm" :model="searchForm" :inline="true">
        <el-form-item label="菜单名称">
          <el-input v-model="searchForm.menuName" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getMenuList()">查询</el-button>
          <el-button type="primary" @click="toAdd()">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="menu__index__table">
      <el-table
        :data="menuList" border stripe height="680"
        row-key="id"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        >
        <el-table-column prop="id" label="ID" align="center"></el-table-column>
        <el-table-column prop="menuName" label="菜单名称" align="center"></el-table-column>
        <el-table-column prop="menuType" label="菜单类型" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.menuType === 0">菜单组</span>
            <span v-if="scope.row.menuType === 1">功能菜单</span>
          </template>
        </el-table-column>
        <el-table-column prop="menuFun" label="菜单功能" align="center"></el-table-column>
        <el-table-column prop="menuSort" label="排序" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button v-if="scope.row.menuType === 0" type="text" size="small" @click="toAdd(scope.row)">新增</el-button>
            <el-button type="text" size="small" @click="toEdit(scope.row)">编辑</el-button>
            <el-button type="text" size="small" @click="deleteMenu(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="600px" :before-close="closeDialog">
      <div class="dialog__form">
        <el-form ref="dialogForm" :model="addForm">
          <el-form-item label="菜单名称" label-width="120px" prop="menuName">
            <el-input v-model="addForm.menuName"></el-input>
          </el-form-item>
          <el-form-item label="父级菜单名称" label-width="120px" prop="parentName">
            <el-input v-model="addForm.parentName"></el-input>
          </el-form-item>
          <el-form-item label="菜单类型" label-width="120px" prop="menuType">
            <el-select v-model="addForm.menuType" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="菜单功能" label-width="120px" prop="menuFun">
            <el-input v-model="addForm.menuFun"></el-input>
          </el-form-item>
          <el-form-item label="排序" label-width="120px" prop="menuSort">
            <el-input v-model="addForm.menuSort"></el-input>
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
import _ from 'lodash'

export default {
  data () {
    return {
      searchForm: {
        menuName: ''
      },
      menuList: [],
      dialogTitle: '添加菜单',
      dialogFormVisible: false,
      addForm: {
        id: 0,
        parentId: 0,
        parentName: '',
        menuName: '',
        menuType: 0,
        menuSort: 0,
        menuFun: ''
      },
      options: [{
        value: 0,
        label: '菜单组'
      }, {
        value: 1,
        label: '功能菜单'
      }]
    }
  },
  mounted () {
    this.getMenuList()
  },
  methods: {
    async getMenuList () {
      this.menuList = []
      const res = await api.sysMenu.list()
      if (res.data.code === 200) {
        _.forEach(res.data.data, item => {
          if (item.menuType === 0) {
            item.children = []
            _.forEach(res.data.data, child => {
              if (child.parentId === item.id) {
                child.parentName = item.menuName
                item.children.push(child)
              }
            })
            this.menuList.push(item)
          }
        })
      }
    },
    closeDialog () {
      this.addForm.id = 0
      this.addForm.menuName = ''
      this.addForm.menuType = 0
      this.addForm.menuSort = 0
      this.addForm.menuFun = ''
      this.addForm.parentName = ''
      this.addForm.parentId = 0
      this.dialogFormVisible = false
    },
    async submit () {
      console.log(this.addForm)
      const res = await api.sysMenu.save(this.addForm, { dismissLoading: false })
      if (res && res.data.code === 200) {
        this.$message.success('操作成功')
        this.closeDialog()
        this.getMenuList()
      }
    },
    toAdd (row) {
      if (row) {
        this.addForm.parentName = row.menuName
        this.addForm.parentId = row.id
        this.addForm.menuType = 1
      }
      this.dialogFormVisible = true
    },
    toEdit (row) {
      console.log(row)
      this.dialogFormVisible = true
      this.addForm.id = row.id
      this.addForm.menuName = row.menuName
      this.addForm.menuType = row.menuType
      this.addForm.menuSort = row.menuSort
      this.addForm.menuFun = row.menuFun
      this.addForm.parentName = row.parentName
      this.addForm.parentId = row.parentId
    },
    deleteMenu (row) {
      this.$confirm(`确定删除菜单: ${row.menuName}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.sysMenu.delete(row.id)
          .then(res => {
            if (res.data.code === 200) {
              this.$message.success('操作成功')
              this.getMenuList()
            }
          })
          .catch(() => {})
      }).catch(() => {})
    }
  }
}
</script>
<style scoped>
</style>
