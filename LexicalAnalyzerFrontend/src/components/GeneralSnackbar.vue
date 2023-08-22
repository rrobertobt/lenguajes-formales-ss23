<template>
  <v-snackbar v-model="snackbar" :color="color" multi-line rounded="pill" location="top">
    <div>
      <v-icon class="mr-1">
        {{ icon }}
      </v-icon>
      <strong>{{ info.text + ' ' }}</strong>
      <br />
      <template v-if="info.details">
        <span style="font-style: italic">Detalles:</span> {{ info.details }}
      </template>
    </div>
    <template #actions>
      <v-btn icon @click="hide">
        <v-icon> mdi-close </v-icon>
      </v-btn>
    </template>
  </v-snackbar>
</template>
<script>
export default {
  props: {
    info: {
      type: Object,
      default: () => ({
        text: '',
        type: '',
        details: ''
      })
    }
  },
  data() {
    return {
      snackbar: false
    }
  },
  computed: {
    color() {
      switch (this.info.type) {
        case 'warn':
          return 'yellow-lighten-3'
        case 'success':
          return 'blue-grey-lighten-4'
        case 'error':
          return 'red-lighten-1'
        default:
          return 'grey-lighten-3'
      }
    },
    icon() {
      switch (this.info.type) {
        case 'warn':
          return 'mdi-alert-circle'
        case 'success':
          return 'mdi-check-circle'
        case 'error':
          return 'mdi-alert'
        default:
          return 'mdi-information'
      }
    }
  },
  methods: {
    show() {
      console.log(this.info)
      this.snackbar = true
    },
    hide() {
      this.snackbar = false
    }
  }
}
</script>
<style scoped></style>
