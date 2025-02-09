<template>
  <div class="qr-container">
    <h1>QR Code</h1>
    <qrcode-vue
        :value="qrValue"
        :size="200"
        level="H"
        render-as="svg"
    />
    <p>PIN: {{ qrPin }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import QrcodeVue from 'qrcode.vue';

const qrValue = ref("");
const qrPin = ref("");

function getQueryParam(param) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(param);
}

onMounted(() => {
  qrPin.value = getQueryParam("pin");
  qrValue.value = `http://34.96.216.198/submission/QR_submit?pin=${qrPin.value}`;
});
</script>

<style>
.qr-container {
  text-align: center;
  margin-top: 50px;
}
</style>