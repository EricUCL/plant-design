<template>
  <div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
      <el-form-item label="选择树种类型" prop="species">
        <el-select v-model="ruleForm.species" placeholder="请选择树种类别">
          <el-option label="杉木" value="shanmu"></el-option>
          <el-option label="松树" value="songshu"></el-option>
          <el-option label="桦木科" value="huamuke"></el-option>
          <el-option label="杨柳科" value="yangliuke"></el-option>
          <el-option label="豆科" value="douke"></el-option>
          <el-option label="樟树" value="zhangshu"></el-option>
          <el-option label="木兰科" value="mulanke"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="预测年份" prop="year">
        <!--        <span class="demonstration">年</span>-->
        <el-date-picker
            v-model="ruleForm.year"
            type="year"
            placeholder="选择年"
            value-format="yyyy">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">预测</el-button>
        <el-button @click="resetForm('ruleForm')">重置条件</el-button>
      </el-form-item>
    </el-form>
    <span id="result" v-show="showResult">预测结果：{{ result }}</span>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      ruleForm: {
        accumulation: '',
        species: '',
        formula: '1'
      },
      result: "",
      showResult: false,
      out: "hehe",
      resultVisible: false,
      rules: {
        // accumulation: [
        //   {required: true, message: '不能为空', trigger: 'blur'},
        //   {type: "number", message: '必须为数字', trigger: 'blur'},
        // ],
        species: [
          {required: true, message: '请选择树种类型', trigger: 'change'}
        ],
        year: [
          {required: true, message: '请选择年份', trigger: 'change'}
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // alert('submit!');
          this.cal()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.showResult = false
    },
    cal() {
      this.request.post("/output-data/forecast", this.ruleForm).then(
          res => {
            console.log(res)
            console.log(this.ruleForm)
            if (res) {
              this.$message.success("预测成功")
            } else {
              this.$message.error("预测失败")
            }
            console.log(res)
            this.result = res.output
            this.showResult = true
          }
      )
    }
  }
}
</script>

<style scoped>

</style>