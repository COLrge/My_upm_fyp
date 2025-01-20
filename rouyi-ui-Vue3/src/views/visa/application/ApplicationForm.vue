<template>

  <el-dialog :title="title" v-model="dialogVisible" width="80%" append-to-body @close="closeDialog">
    <el-form ref="applicationRef" :model="form" :rules="rules" label-width="200px" :inline="true"
             class="demo-form-inline" :disabled="props.readonly">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Name (Passport MRZ)" prop="name">
            <el-input v-model="form.name" placeholder="Please enter Name as in Passport MRZ format"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Matric Number" prop="matricNumber">
            <el-input v-model="form.matricNumber" placeholder="Please enter Matric Number"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Passport Number" prop="passportNumber">
            <el-input v-model="form.passportNumber" placeholder="Please enter Passport Number"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Email Address" prop="email">
            <el-input v-model="form.email" placeholder="Please enter Email Address"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider direction="horizontal"></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Address in Malaysia" prop="malaysiaAddress">
            <el-input v-model="form.malaysiaAddress" type="textarea" placeholder="Please enter address"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Address Overseas" prop="overseasAddress">
            <el-input v-model="form.overseasAddress" type="textarea" placeholder="Please enter address"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider direction="horizontal"></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Course Level" prop="courseLevel">
            <el-radio-group v-model="form.courseLevel">
              <el-radio label="1">Bachelor</el-radio>
              <el-radio label="2">Master</el-radio>
              <el-radio label="3">Doctor of philosophy</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

      </el-row>
      <el-divider direction="horizontal"></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Nationality" prop="nationality">
            <el-input v-model="form.nationality" placeholder="Please enter Nationality"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Mobile Number" prop="mobileNumber">
            <el-input v-model="form.mobileNumber" placeholder="Please enter Mobile Number (WhatsApp)"/>
          </el-form-item>
        </el-col>

      </el-row>
      <el-divider direction="horizontal"></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Application Location" prop="applicationLocation">
            <el-radio-group v-model="form.applicationLocation">
              <el-radio label="1">Inside Malaysia</el-radio>
              <el-radio label="2">Outside Malaysia</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider direction="horizontal"></el-divider>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Course Name" prop="courseName">
            <el-input v-model="form.courseName" placeholder="Please enter Course Name"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Faculty/School Name" prop="facultyName">
            <el-input v-model="form.facultyName" placeholder="Please enter Faculty/Institute/School Name"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Supervisor Name" prop="supervisorName">
            <el-input v-model="form.supervisorName" placeholder="Please enter Supervisor/Advisor Name"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Sponsorship Status" prop="sponsorshipStatus">
            <el-radio-group v-model="form.sponsorshipStatus">
              <el-radio label="1">Self Finance</el-radio>
              <el-radio label="2">Sponsorship</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Study Status" prop="studyStatus">
            <el-select v-model="form.studyStatus" placeholder="Please select study status" class="full-width-input">
              <el-option label="CONTINUE/ PROBATION" value="1"/>
              <el-option label="THESIS SUBMITTED" value="2"/>
              <el-option label="DROPPED" value="3"/>
              <el-option label="DEFER" value="4"/>
              <el-option label="COMPLETED" value="5"/>
              <el-option label="TERMINATED" value="6"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Marital Status" prop="maritalStatus">
            <el-radio-group v-model="form.maritalStatus">
              <el-radio label="1">Single</el-radio>
              <el-radio label="2">Married</el-radio>
              <el-radio label="3">Divorced</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider direction="horizontal"></el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Date of Birth" prop="dateOfBirth">
            <el-date-picker
                v-model="form.dateOfBirth"
                type="date"
                placeholder="Please select Date of Birth"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Current Pass Validity" prop="currentPassValidity">
            <el-date-picker
                v-model="form.currentPassValidity"
                type="date"
                placeholder="Please select Validity Date of Current Pass"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Travel Doc Issue Date" prop="travelDocumentIssueDate">
            <el-date-picker
                v-model="form.travelDocumentIssueDate"
                type="date"
                placeholder="Please select Travel Document Issued Date"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Travel Doc Expiry Date" prop="travelDocumentExpiryDate">
            <el-date-picker
                v-model="form.travelDocumentExpiryDate"
                type="date"
                placeholder="Please select Travel Document Valid Until"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Latest Entry Date" prop="lastEntryDate">
            <el-date-picker
                v-model="form.lastEntryDate"
                type="date"
                placeholder="Please select Latest date enter Malaysia"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider direction="horizontal"></el-divider>


      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Travel Document Type" prop="travelDocumentType">
            <el-radio-group v-model="form.travelDocumentType">
              <el-radio :label="1">INTERNATIONAL PASSPORT</el-radio>
              <el-radio :label="2">DIPLOMATIC PASSPORT</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Current Pass Type" prop="currentPassType">
            <el-select v-model="form.currentPassType" placeholder="Please select pass type" class="full-width-input">
              <el-option label="STUDENT PASS" value="1"/>
              <el-option label="SOCIAL PASS" value="2"/>
              <el-option label="FIRST SPECIAL PASS" value="3"/>
              <el-option label="SECOND SPECIAL PASS" value="4"/>
            </el-select>
          </el-form-item>
        </el-col>

      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="Insurance Company" prop="insuranceCompany">
            <el-input v-model="form.insuranceCompany" placeholder="Please enter Insurance Cover Note Company"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="Remarks" prop="remarks">
            <el-input v-model="form.remarks" type="textarea" placeholder="Please enter remarks"/>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 添加文件上传部分 -->
      <el-divider direction="horizontal">Required Documents</el-divider>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Boarding Pass" prop="boardingPassImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'boardingPassImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('boardingPassImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>
            <view
                v-if="form.boardingPassImg"
                @click="downloadFile(form.boardingPassImg)"
                class="download-link"> <!-- 添加 CSS 类 -->
            Download
            </view>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="Passport" prop="passportImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'passportImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('passportImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>
            <view v-if="form.passportImg" @click="downloadFile(form.passportImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Old Passport" prop="oldPassportImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'oldPassportImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('oldPassportImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.oldPassportImg"  @click="downloadFile(form.oldPassportImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="Registration Slip" prop="registrationSlipImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'registrationSlipImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('registrationSlipImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.registrationSlipImg"  @click="downloadFile(form.registrationSlipImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Academic Result" prop="academicResultImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'academicResultImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('academicResultImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.academicResultImg"  @click="downloadFile(form.academicResultImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="Attendance Report" prop="attendanceReportImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'attendanceReportImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('attendanceReportImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.attendanceReportImg"  @click="downloadFile(form.attendanceReportImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Progress Report" prop="progressReportImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'progressReportImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('progressReportImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.progressReportImg"  @click="downloadFile(form.progressReportImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="Supporting Letter" prop="supportingLetterImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'supportingLetterImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('supportingLetterImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.supportingLetterImg"  @click="downloadFile(form.supportingLetterImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="EMGS Payment" prop="emgsPaymentImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'emgsPaymentImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('emgsPaymentImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.emgsPaymentImg"  @click="downloadFile(form.emgsPaymentImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="UPM Payment" prop="upmPaymentImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'upmPaymentImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('upmPaymentImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
             </el-upload>
            <view v-if="form.upmPaymentImg"  @click="downloadFile(form.upmPaymentImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="Insurance Cover Note" prop="insuranceCoverNoteImg">
            <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-success="(res) => handleUploadSuccess(res, 'insuranceCoverNoteImg')"
                :on-error="handleUploadError"
                :file-list="getFileList('insuranceCoverNoteImg')"
                list-type="picture-card"
                :on-preview="handlePreview"
                :limit="1"
            >
              <el-icon><Plus /></el-icon>
            </el-upload>
            <view v-if="form.insuranceCoverNoteImg"  @click="downloadFile(form.insuranceCoverNoteImg)" class="download-link">Download</view>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-dialog
        title="Preview"
        v-model="previewDialogVisible"
        width="30%"
        append-to-body
    >
      <img :src="previewUrl" alt="Insurance Cover Note" style="width: 100%">
    </el-dialog>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="closeDialog">{{ props.readonly ? 'Close' : 'Cancel' }}</el-button>
        <el-button type="primary" @click="submitForm" v-if="!props.readonly">Confirm</el-button>
      </div>
    </template>
  </el-dialog>
</template>


<script setup>
import {ref, reactive, toRefs, defineProps, defineEmits, watch} from 'vue';
import {ElMessage} from 'element-plus';
import {getApplication, addApplication, updateApplication} from "@/api/system/application";
import {Plus} from '@element-plus/icons-vue'
import {getToken} from '@/utils/auth'
import useUserStore from "@/store/modules/user.js";

const props = defineProps({
  visible: Boolean,
  title: String,
  applicationId: [Number, String],
  readonly: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['update:visible', 'refresh']);

const applicationRef = ref(null);
const dialogVisible = ref(props.visible);
const previewUrl = ref('');
const previewDialogVisible = ref(false);
const data = reactive({

  form: {
    id: null,
    applicantId: null,
    name: null,
    matricNumber: null,
    passportNumber: null,
    email: null,
    malaysiaAddress: null,
    overseasAddress: null,
    courseLevel: null,
    nationality: null,
    mobileNumber: null,
    applicationLocation: null,
    courseName: null,
    facultyName: null,
    supervisorName: null,
    sponsorshipStatus: null,
    studyStatus: null,
    maritalStatus: null,
    dateOfBirth: null,
    currentPassValidity: null,
    travelDocumentIssueDate: null,
    travelDocumentExpiryDate: null,
    lastEntryDate: null,
    travelDocumentType: null,
    currentPassType: null,
    insuranceCompany: null,
    status: null,
    reviewerId: null,
    remarks: null,
    createdAt: null,
    updatedAt: null,
    boardingPassImg: null,
    passportImg: null,
    oldPassportImg: null,
    registrationSlipImg: null,
    academicResultImg: null,
    attendanceReportImg: null,
    progressReportImg: null,
    supportingLetterImg: null,
    emgsPaymentImg: null,
    upmPaymentImg: null,
    insuranceCoverNoteImg: null
  },
  rules: {
    applicantId: [{required: true, message: "Applicant ID is required", trigger: "blur"}],
    name: [{required: true, message: "Name is required", trigger: "blur"}],
    matricNumber: [{required: true, message: "Matric Number is required", trigger: "blur"}],
    passportNumber: [{required: true, message: "Passport Number is required", trigger: "blur"}],
    email: [
      {required: true, message: "Email is required", trigger: "blur"},
      {type: 'email', message: 'Please input correct email address', trigger: ['blur', 'change']}
    ],
    malaysiaAddress: [{required: true, message: "Address in Malaysia is required", trigger: "blur"}],
    overseasAddress: [{required: true, message: "Address Overseas is required", trigger: "blur"}],
    courseLevel: [{required: true, message: "Course Level is required", trigger: "blur"}],
    nationality: [{required: true, message: "Nationality is required", trigger: "blur"}],
    mobileNumber: [{required: true, message: "Mobile Number is required", trigger: "blur"}],
    applicationLocation: [{required: true, message: "Application Location is required", trigger: "blur"}],
    courseName: [{required: true, message: "Course Name is required", trigger: "blur"}],
    facultyName: [{required: true, message: "Faculty/School Name is required", trigger: "blur"}],
    supervisorName: [{required: true, message: "Supervisor Name is required", trigger: "blur"}],
    sponsorshipStatus: [{required: true, message: "sponsorship Status is required", trigger: "blur"}],
    studyStatus: [{required: true, message: "study Status is required", trigger: "blur"}],
    maritalStatus: [{required: true, message: "marital Status is required", trigger: "blur"}],
    dateOfBirth: [{required: true, message: "Date of Birth is required", trigger: "blur"}],
    currentPassValidity: [{required: true, message: "Current Pass Validity is required", trigger: "blur"}],
    travelDocumentIssueDate: [{required: true, message: "Travel Document Issue Date is required", trigger: "blur"}],
    travelDocumentExpiryDate: [{required: true, message: "Travel Document Expiry Date is required", trigger: "blur"}],
    lastEntryDate: [{required: true, message: "Latest Entry Date is required", trigger: "blur"}],
  }
});

const {form, rules} = toRefs(data);

watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal;
  if (newVal && props.applicationId) {
    getApplicationData(props.applicationId);
  } else {
    resetForm();
    // 获取当前登录的用户
    useUserStore().getInfo().then(response => {
      form.value.email = response.user.email
      form.value.name = response.user.nickName
      form.value.mobileNumber = response.user.phonenumber
      // debugger
    })
  }
});
const handlePreview = (file) => {

  debugger
  previewUrl.value = file.url
  previewDialogVisible.value = true
  // window.open(file.url)
}
const getApplicationData = async (id) => {
  try {
    const response = await getApplication(id);
    Object.assign(form.value, response.data);
  } catch (error) {
    console.error('Failed to get application data:', error);
    ElMessage.error('Failed to load application data');
  }
};

const submitForm = () => {
  applicationRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.value.id) {
          await updateApplication(form.value);
          ElMessage.success('Application updated successfully');
        } else {
          await addApplication(form.value);
          ElMessage.success('Application added successfully');
        }
        closeDialog();
        emit('refresh');
      } catch (error) {
        console.error('Failed to submit form:', error);
        ElMessage.error('Failed to submit application');
      }
    }
  });
};

const closeDialog = () => {
  dialogVisible.value = false;
  emit('update:visible', false);
  resetForm();
};

const resetForm = () => {
  Object.assign(form.value, {
    id: null,
    applicantId: null,
    name: null,
    matricNumber: null,
    passportNumber: null,
    email: null,
    malaysiaAddress: null,
    overseasAddress: null,
    courseLevel: null,
    nationality: null,
    mobileNumber: null,
    applicationLocation: null,
    courseName: null,
    facultyName: null,
    supervisorName: null,
    sponsorshipStatus: null,
    studyStatus: null,
    maritalStatus: null,
    dateOfBirth: null,
    currentPassValidity: null,
    travelDocumentIssueDate: null,
    travelDocumentExpiryDate: null,
    lastEntryDate: null,
    travelDocumentType: null,
    currentPassType: null,
    insuranceCompany: null,
    status: null,
    reviewerId: null,
    remarks: null,
    createdAt: null,
    updatedAt: null,
    boardingPassImg: null,
    passportImg: null,
    oldPassportImg: null,
    registrationSlipImg: null,
    academicResultImg: null,
    attendanceReportImg: null,
    progressReportImg: null,
    supportingLetterImg: null,
    emgsPaymentImg: null,
    upmPaymentImg: null,
    insuranceCoverNoteImg: null
  });
  applicationRef.value?.resetFields();
};
// 添加文件上传相关的响应式数据
const uploadFileUrl = import.meta.env.VITE_APP_BASE_API + '/common/upload'
const headers = ref({
  Authorization: 'Bearer ' + getToken()
})

// 处理文件上传成功
const handleUploadSuccess = (response, field) => {
  if (response.code === 200) {
    form.value[field] = response.url
    ElMessage.success('Upload successful')
  } else {
    ElMessage.error(response.msg)
  }
}

// 处理文件上传错误
const handleUploadError = () => {
  ElMessage.error('Upload failed')
}

// 获取文件列表用于回显
const getFileList = (field) => {
  if (form.value[field]) {
    return [{
      name: form.value[field].split('/').pop(),
      url: import.meta.env.VITE_APP_BASE_API + form.value[field]
    }]
  }
  return []
}

// Download文件
const downloadFile = (fileUrl) => {
  const link = document.createElement('a');
  link.href = import.meta.env.VITE_APP_BASE_API + fileUrl;
  link.download = fileUrl.split('/').pop();
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}

defineExpose({
  dialogVisible,
  form
});
</script>

<style scoped>
.dialog-footer {
  text-align: right;
  margin-top: 20px;
}
</style>
<style scoped>
.dialog-footer {
  text-align: right;
  margin-top: 20px;
}

.el-form-item {
  width: 100%;
  margin-bottom: 22px;
}

.el-input, .el-select, .el-date-picker {
  width: 100%;
}

.el-textarea__inner {
  min-height: 80px;
}

.el-form-item__content {
  display: flex;
  align-items: center;
}

.el-radio-group {
  width: 100%;
  display: flex;
  justify-content: flex-start;
}

.el-col {
  margin-bottom: 10px;
}

.upload-demo {
  width: 100%;
}

.el-upload {
  width: 100%;
}

.el-upload--picture-card {
  width: 148px;
  height: 148px;
  line-height: 146px;
}

.download-link {
  color: blue; /* 蓝色 */
  cursor: pointer; /* 手型光标 */
  text-decoration: none; /* 去掉下划线（可选） */
}

.download-link:hover {
  color: darkblue; /* 悬停时变为深蓝色 */
}

</style>
