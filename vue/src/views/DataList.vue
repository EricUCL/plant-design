<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 200px" v-model="username"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">搜索</el-button>
      <!--      <el-button style="margin-left: 5px" @click="add" visible="false">新增</el-button>-->
      <el-button style="margin-left: 5px" @click="exp">导出</el-button>
      <el-popconfirm
          title="确定删除吗？"
          @confirm="delBatch">
        <el-button slot="reference">批量删除</el-button>
      </el-popconfirm>
      <!--        <el-button style="margin-left: 5px" @click="delBatch">批量删除</el-button>-->
    </div>
    <el-table :data="tableData"
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="100">
      </el-table-column>
      <el-table-column prop="year" label="年份" width="140">
      </el-table-column>
      <el-table-column prop="species" :formatter="speciesFormatter" label="树种" width="140">
      </el-table-column>
      <el-table-column prop="accumulation" label="蓄积量" width="120">
      </el-table-column>
      <el-table-column prop="formula" :formatter="formulaFormatter" label="选用公式" width="200">
      </el-table-column>
      <el-table-column prop="output" label="计算结果" width="190">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <!--          <el-button-->
          <!--              size="mini"-->
          <!--              @click="handleEdit(scope.row)">编辑-->
          <!--          </el-button>-->
          <el-popconfirm
              title="这是一段内容确定删除吗？"
              @confirm="handleDelete(scope.row)">
            <el-button
                size="mini" slot="reference" type="primary">删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="form" label-width="80px">
        <el-form-item label="树种">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addNew">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      speciesTypeList: [
        {species: "shanmu", idTypeName: "杉木"},
        {species: "huamuke", idTypeName: "桦木科"},
        {species: "songshu", idTypeName: "松树"},
        {species: "yangliuke", idTypeName: "杨柳科"},
        {species: "douke", idTypeName: "豆科"},
        {species: "zhangshu", idTypeName: "樟树"},
        {species: "mulanke", idTypeName: "木兰科"}
      ],
      formulaTypeList: [
        {formula: 1, idTypeName: "生物量回归模型"},
        {formula: 2, idTypeName: "相对生长关系模型"},
        {formula: 3, idTypeName: "生物量转换因子连续函数法"},
        {formula: 4, idTypeName: "IPCC估算模型"}
      ],
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    load() {
      this.request.get("/output-data/page?", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(
          res => {
            console.log(res)
            this.tableData = res.records;
            this.total = res.total
          }
      )
    },
    add() {
      this.dialogFormVisible = true;
      this.form = {}
    },
    addNew() {
      this.dialogFormVisible = false
      this.request.post("/output-data", this.form).then(
          res => {
            console.log(res)
            if (res) {
              this.$message.success("保存成功")
            } else {
              this.$message.error("保存失败")
            }
            this.load()
          }
      )
    },
    handleEdit(row) {
      console.log(row)
      this.form = Object.assign({}, row)
      this.dialogFormVisible = true;
    },
    handleDelete(row) {
      console.log("hello")
      this.request.delete("/output-data/" + row.id).then(
          res => {
            console.log(res)
            if (res) {
              this.$message.success("保存成功")
            } else {
              this.$message.error("保存失败")
            }
            this.load()
          }
      )
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    speciesFormatter(row, column) {
      var value = "";
      for (var i = 0; i < this.speciesTypeList.length; i++) {
        var item = this.speciesTypeList[i];
        if (row.species == item.species) {
          value = item.idTypeName;
          break;
        }
      }
      return value;
    },
    formulaFormatter(row, column) {
      var value = "";
      for (var i = 0; i < this.formulaTypeList.length; i++) {
        var item = this.formulaTypeList[i];
        if (row.formula == item.formula) {
          value = item.idTypeName;
          break;
        }
      }
      return value;
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/output-data/delete/batch", ids).then(
          res => {
            console.log(res)
            if (res) {
              this.$message.success("批量删除成功")
            } else {
              this.$message.error("批量删除失败")
            }
            this.load()
          }
      )
    },
    exp() {
      window.open("http://localhost:9090/output-data/export")
    }
  }
}
</script>

<style scoped>

</style>