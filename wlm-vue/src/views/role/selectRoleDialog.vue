<template>
  <el-dialog :title="title" :visible.sync="visible" width="600px" :before-close="closeDialog">
    <div class="role__dialog__search">
      <span>角色名称: </span>
      <el-input v-model="roleSearchText" placeholder="请输入角色名称"></el-input>
      <el-button type="primary" @click="getList">查询</el-button>
    </div>
    <div class="role__dialog__table">
      <el-table :data="data" border stripe height="300" highlight-current-row @current-change="handleTableSelect">
        <el-table-column v-if="!isSigle" type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" align="center"></el-table-column>
        <el-table-column prop="roleNo" label="角色编号" align="center"></el-table-column>
        <el-table-column prop="roleName" label="角色名称" align="center"></el-table-column>
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
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeDialog">取消</el-button>
      <el-button type="primary" @click="confirm">确定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import api from '@/webapi'

export default {
  props: ['visible', 'title', 'isSigle'],
  data () {
    return {
      roleSearchText: '',
      data: [],
      selectRole: {},
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
        roleName: this.roleSearchText
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
    handleTableSelect (row) {
      this.selectRole = row
    },
    closeDialog () {
      this.$emit('update:visible', false)
    },
    confirm () {
      this.$emit('confirm', this.selectRole)
      this.closeDialog()
    }
  }
}
</script>
<style scoped>
.role__dialog__search {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}
.role__dialog__search span {
  display: inline-block;
  width: 140px;
}
.role__dialog__table {
  height: 300px;
  margin-bottom: 20px;
}

</style>
