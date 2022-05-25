<template>
  <div >
    <el-radio-group v-model="radio" @change="onRatioChange" >
      <el-radio :label="1">模型1</el-radio>
      <el-radio :label="2">模型2</el-radio>
      <el-radio :label="3">模型3</el-radio>
      <el-radio :label="4">模型4</el-radio>
    </el-radio-group>
    <el-table
        style="width: 100%" :data="tableData">
      <el-table-column
          label="树种"
          prop="species"
          :formatter="speciesFormatter">
      </el-table-column>
      <el-table-column
          label="计算公式"
          prop="formula">
      </el-table-column>
      <el-table-column
          align="right">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="修改参数" :visible.sync="dialogFormVisible" style="width: 900px" :rules="rules">
      <el-form :model="form" label-width="80px" ref="ruleForm" class="demo-ruleForm">
        <el-form-item label="参数1">
          <el-input v-model="form.parametersOne" autocomplete="off" style="width: 100px"></el-input>
        </el-form-item>
        <el-form-item label="参数2">
          <el-input v-model="form.parametersTwo" autocomplete="off" style="width: 100px"></el-input>
        </el-form-item>
        <el-form-item label="参数3" v-show="thirdVisible">
          <el-input v-model="form.parametersThree" autocomplete="off" style="width: 100px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addNew('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AlterParameters",
  data() {
    return {
      radio:1,
      species: "",
      dialogFormVisible: false,
      thirdVisible: false,
      form: {},
      tableData: [],
      rules: {
        parametersOne: [
          {required: true, message: "请输入数字", trigger: "blur"},
          {
            transform(value) {
              return Number(value);
            },
            validator(rule, value, callback) {
              if (Number.isFinite(value)) {
                callback();
              } else {
                callback(new Error("请输入数字"));
              }
            },
            trigger: "blur",
          }
        ],
        parametersTwo: [
          {required: true, message: "请输入数字", trigger: "blur"},
          {
            transform(value) {
              return Number(value);
            },
            validator(rule, value, callback) {
              if (Number.isFinite(value)) {
                callback();
              } else {
                callback(new Error("请输入数字"));
              }
            },
            trigger: "blur",
          }
        ],
        parametersThree: [
          {required: true, message: "请输入数字", trigger: "blur"},
          {
            transform(value) {
              return Number(value);
            },
            validator(rule, value, callback) {
              if (Number.isFinite(value)) {
                callback();
              } else {
                callback(new Error("请输入数字"));
              }
            },
            trigger: "blur",
          }
        ]
      },
      speciesTypeList: [
        {species: "shanmu", idTypeName: "杉木"},
        {species: "huamuke", idTypeName: "桦木科"},
        {species: "songshu", idTypeName: "松树"},
        {species: "yangliuke", idTypeName: "杨柳科"},
        {species: "douke", idTypeName: "豆科"},
        {species: "zhangshu", idTypeName: "樟树"},
        {species: "mulanke", idTypeName: "木兰科"}
      ],
    };
  },
  create() {
    this.load();
  },
  methods: {

    handleEdit(index, row) {
      console.log(row)
      this.form = Object.assign({}, row)
      this.dialogFormVisible = true;
      this.species = row.species
      this.thirdVisible = this.radio === 4;
      console.log(this.radio)
    },
    addNew(formName) {
      this.dialogFormVisible = false
      this.request.post("/parameters/alter", this.form,
      ).then(
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
    onRatioChange(value) {
      console.log('改变之后的值是:' + value)
      this.load()
    },
    speciesFormatter(row, column) {
      var value = "";
      for (var i = 0; i < this.speciesTypeList.length; i++) {
        var item = this.speciesTypeList[i];
        if (row.species === item.species) {
          value = item.idTypeName;
          break;
        }
      }
      return value;
    },
    load() {
      this.request.get("/parameters/all", {
        params: {
          formula: this.radio,
        }
      }).then(
          res => {
            console.log(res)
            this.tableData = res;
          }
      )
    },
  }
}
</script>

<style scoped>

</style>