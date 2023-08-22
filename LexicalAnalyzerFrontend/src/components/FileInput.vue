<template>
  <div class="text-center">
    <v-btn size="large">
      <v-icon class="mx-2" size="large"> mdi-file-document-arrow-right-outline </v-icon>
      <strong>Leer archivo</strong>

      <v-dialog v-model="dialog" activator="parent" width="32vw">
        <v-card class="dialog-card rounded-xl">
          <v-card-title class="dialog-title">
            <h3>Leer desde archivo</h3>
            <v-btn icon variant="flat" @click="cancelDialog">
              <v-icon>mdi-close</v-icon>
            </v-btn>
          </v-card-title>
          <p style="font-size: 0.9rem; color: gray; width: 90%; text-align: left" class="ml-4 mb-4">
            Aquí puedes seleccionar un archivo de texto plano para leerlo y analizarlo
          </p>
          <v-card-text>
            <div>
              <v-file-input
                ref="inputfile"
                label="Archivo de texto"
                variant="outlined"
                :clearable="false"
                @change="validateFile"
              />
              <p v-if="file" class="my-3">
                <v-icon class="mb-1 mr-1">mdi-folder-information</v-icon>
                <strong>Tipo de archivo detectado: </strong>
                <br />
                <code
                  ><v-icon v-if="error" class="mb-1 mr-1"> mdi-file-question-outline </v-icon>
                  <v-icon v-else class="mb-1 mr-1"> mdi-file-check-outline </v-icon
                  >{{ fileType }}</code
                >
              </p>
              <p v-if="error" class="my-4">
                <span>
                  <v-icon color="red" class="mr-2"> mdi-alert-circle </v-icon>
                  <strong>Ocurrió un error al leer el archivo:</strong></span
                >
                <br />
                <span style="color: red">{{ error }}</span>
              </p>
            </div>
          </v-card-text>

          <v-card-actions class="justify-end">
            <v-btn
              variant="tonal"
              color="deep-purple-accent-3"
              :disabled="disableAcceptButton"
              @click="submitFile"
            >
              Usar archivo
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-btn>
  </div>
</template>
<script>
import { FILE_ERRORS } from '@/utils/constants.js'
export default {
  emits: { fileRead: null },
  data() {
    return {
      dialog: false,
      error: '',
      thereIsFile: false,
      file: null,
      fileType: ''
    }
  },
  computed: {
    disableAcceptButton() {
      if (!this.thereIsFile) {
        return true
      } else if (this.error) {
        return true
      } else {
        return false
      }
    }
  },
  methods: {
    validateFile(event) {
      console.log(event)
      const file = event.target.files[0]
      if (file) {
        this.file = file
        this.thereIsFile = true
        // Obtenemos la extensión del archivo
        const fileNameParts = file.name.split('.')
        const fileExtension = fileNameParts[fileNameParts.length - 1].toLowerCase()
        console.log(file.type)
        // Primero validamos que venga el tipo de archivo para validarlo
        if (file.type && file.type !== 'text/plain') {
          this.fileType = file.type
          this.error = FILE_ERRORS.TYPE_INVALID
        } else if (!file.type) {
          // En caso de que no venga el tipo de archivo, intentamos validarlo por la extensión
          if (fileExtension !== 'txt' && fileExtension !== 'py') {
            // No se pudo detectar una extension valida tampoco
            this.fileType = 'Desconocido, extension: .' + fileExtension
            this.error = FILE_ERRORS.COULDNT_DETECT_TYPE
          } else {
            this.fileType = 'Reconocido, con extension: .' + fileExtension
            this.error = null
          }
        } else {
          this.fileType = file.type
          this.error = null
        }
      }
    },
    submitFile() {
      // emitimos el evento para pasarle el contenido del archivo al padre
      // debemos leer el archivo que está en data
      if (this.file) {
        const reader = new FileReader()
        reader.onload = () => {
          console.log(reader.result)
          // Emitir el evento con el contenido del archivo como argumento
          this.$emit('fileRead', reader.result)
          this.cancelDialog()
        }
        reader.onerror = () => {
          this.error = FILE_ERRORS.COULDNT_READ_FILE
        }
        reader.readAsText(this.file)
      }
    },
    cancelDialog() {
      this.dialog = false
      this.error = ''
      this.thereIsFile = false
      this.file = null
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
</style>
