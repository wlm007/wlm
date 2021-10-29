<template>
  <div class="user__index">
    <div class="user__index__form">
      <el-form ref="userIndexForm" :model="form" :inline="true">
        <el-form-item label="姓名">
          <el-input v-model="form.username" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="user__index__table">
      <el-table :data="data" border stripe height="500">
        <el-table-column prop="username" label="用户姓名" align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
        <el-table-column prop="age" label="年龄" align="center"></el-table-column>
        <el-table-column prop="deptName" label="部门" align="center"></el-table-column>
        <el-table-column prop="roleName" label="角色" align="center"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="toDetail(scope.row)" type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
            <el-button type="text" size="small">删除</el-button>
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

</template>

<script>
import api from '@/webapi'

export default{
  data () {
    return {
      data: [],
      form: {
        username: ''
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
        username: this.form.username
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
        this.pageNo = 1
      }
    },
    handleCurrentChange (val) {
      this.pageNo = val
      this.getList()
    },
    toDetail (row) {
      console.log(row)
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
  height: calc(100% - 200px);
}
</style>
