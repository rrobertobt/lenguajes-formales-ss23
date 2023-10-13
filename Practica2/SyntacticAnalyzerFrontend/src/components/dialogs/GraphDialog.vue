<template>
  <div class="text-center">
    <v-btn icon size="x-small" variant="outlined" color="deep-purple-darken-4">
      <v-icon> mdi-graph </v-icon>
      <v-dialog v-model="dialog" activator="parent" width="40vw">
        <v-card class="dialog-card rounded-xl">
          <v-card-title class="dialog-title">
            <h3 style="max-width: 50%">
              Gráfico para: <code>{{ lexeme }}</code>
            </h3>
            <v-btn icon variant="flat" @click="dialog = false">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-card-title>
          <v-card-text>
            <div class="text-center">
              <v-progress-circular
                v-if="loading"
                color="blue-lighten-3"
                indeterminate
                :size="60"
                :width="9"
              ></v-progress-circular>
              <img v-else :src="imageData" alt="Image" class="graph-image" />
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-btn>
  </div>
</template>
<script>
import { API_BASE_URL } from '@/utils/constants.js'
export default {
  props: {
    lexeme: {
      type: String,
      default: () => ''
    }
  },
  data() {
    return {
      dialog: false,
      imageData: '',
      loading: false
    }
  },
  watch: {
    dialog(val) {
      if (val) {
        this.getImage()
      }
    }
  },
  methods: {
    async getImage() {
      this.loading = true
      const response = await fetch(`${API_BASE_URL}/graph`, {
        method: 'POST',
        headers: {
          'Content-Type': 'text/plain, charset=UTF-8'
        },
        body: this.lexeme
      })
      const data = await response.text()
      this.imageData = `data:image/png;base64,${data}`
      this.loading = false
    }
  }
}
</script>
<style scoped>
.dialog-card {
  padding: 1.3rem;
}

.dialog-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.graph-image {
  width: 100%;
  height: auto;
}
</style>
