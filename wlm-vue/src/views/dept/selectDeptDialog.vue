<template>
  <el-dialog :title="title" :visible.sync="visible" width="600px" :before-close="closeDialog">
    <div class="dept__tree">
      <el-input placeholder="请输入部门名称" v-model="deptFilterText"></el-input>
      <el-tree
        class="dept__tree__show"
        ref="deptTree"
        :data="treeData"
        :props="defaultProps"
        node-key="deptNo"
        :default-expanded-keys="['root']"
        :default-checked-keys="['root']"
        current-node-key="root"
        :highlight-current="true"
        :filter-node-method="deptFilterNode"
        :show-checkbox="!isSigle"
        accordion
        @node-click="handleNodeClick">
      </el-tree>
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
      treeData: [],
      deptDetail: {},
      deptFilterText: '',
      defaultProps: {
        children: 'children',
        label: 'deptName'
      }
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
    async getDeptTree () {
      const res = await api.sysDept.treeList()
      if (res.data.code === 200) {
        this.treeData = res.data.data
        this.deptDetail = res.data.data[0]
      }
    },
    closeDialog () {
      this.$emit('update:visible', false)
    },
    deptFilterNode (value, data) {
      if (!value) return true
      return data.deptName.indexOf(value) !== -1
    },
    handleNodeClick (data) {
      this.deptDetail = data
    },
    confirm () {
      if (this.isSigle) {
        this.$emit('confirm', this.deptDetail)
      } else {
        this.$emit('confirm', this.$refs.deptTree.getCheckedNodes())
      }
      this.closeDialog()
    }
  }
}
</script>
<style scoped>

</style>
