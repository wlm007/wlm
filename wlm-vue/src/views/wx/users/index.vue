<template>
  <div class="wx_users_index">
    <div class="wx_users_form">
      <el-form ref="indexForm" :inline="true">
        <el-form-item>
          <el-button type="primary" @click="getList()">订阅用户</el-button>
          <el-button type="primary" @click="getBlackList()">黑名单</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="wx_users_index_table">
      <el-table
        :data="list" border stripe height="670"
        row-key="id"
        >
        <el-table-column prop="id" label="ID" align="center" width="40"></el-table-column>
        <el-table-column prop="subscribe" label="是否订阅" align="center" width="80"></el-table-column>
        <el-table-column prop="openid" label="openid" align="center"></el-table-column>
        <el-table-column prop="language" label="用户语言" align="center"></el-table-column>
        <el-table-column prop="subscribeTime" label="关注时间" align="center"></el-table-column>
        <el-table-column prop="unionid" label="unionid" align="center"></el-table-column>
        <el-table-column prop="remark" label="备注" align="center"></el-table-column>
        <el-table-column prop="groupid" label="分组ID" align="center"></el-table-column>
        <el-table-column prop="subscribeScene" label="关注渠道" align="center"></el-table-column>
        <el-table-column prop="qrScene" label="二维码扫码场景" align="center"></el-table-column>
        <el-table-column prop="qrSceneStr" label="二维码扫码场景str" align="center"></el-table-column>
        <el-table-column prop="tagidList" label="用户标签" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="remark(scope.row)">备注</el-button>
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

export default {
  data () {
    return {
      msg: '',
      list: [],
      pageNo: 1,
      pageSize: 10,
      total: 0
    }
  },
  mounted () {
    this.getList()
  },
  methods: {
    async getList () {
      const res = await api.wxUsers.list(this.pageNo, this.pageSize)
      if (res.data.code === 200) {
        this.list = res.data.data.data
        this.total = res.data.data.total
      }
    },
    async getBlackList () {
      const res = await api.wxUsers.getBlackList(this.pageNo, this.pageSize)
      if (res.data.code === 200) {
        this.list = res.data.data.data
        this.total = res.data.data.total
      }
    },
    remark (row) {
      this.$prompt('请输入备注', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: '',
        inputErrorMessage: '请输入备注'
      }).then(({ value }) => {
        api.wxUsers.userRemark(row.openid, value).then(res => {
          if (res.data.code === 200) {
            this.$message.success('备注成功')
            this.getList()
          }
        })
      }).catch(() => {})
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
    }
  }
}
</script>
