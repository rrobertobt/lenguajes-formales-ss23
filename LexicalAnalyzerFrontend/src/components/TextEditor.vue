<template>
  <div class="code-container">
    <h4 class="mb-n2">Escribe el codigo aqui:</h4>
    <span>
      <v-chip
        class="mr-3 mb-1"
        color="red"
        prepend-icon="mdi-notification-clear-all"
        @click="clearCode"
      >
        Limpiar
      </v-chip>
      <span style="font-size: 1rem">
        El cursor está en: L<span class="cursor-position-info">{{ line }}</span
        >:C<span class="cursor-position-info">{{ column }}</span>
        <v-icon class="mx-3"> mdi-form-textbox </v-icon>
      </span>
    </span>
    <div class="code-area">
      <textarea
        id="editor"
        ref="editor"
        v-model="code"
        class="code-input"
        spellcheck="false"
        @input="handleInput"
        @keydown="handleKeyDown"
        @click="calculateCursorPosition"
      ></textarea>
      <pre id="code-block" ref="codeBlock" class="code-display"
        >{{ code }}
      </pre>
    </div>
    <div class="code-actions">
      <FileInput @file-read="readTextFromFile($event)" />
      <v-btn size="large" :loading="loading" color="amber-darken-2" @click="analyzeCode">
        <v-icon class="mx-2" size="large"> mdi-cog-play-outline </v-icon>
        <strong>Analizar</strong>
      </v-btn>
    </div>
  </div>
</template>
<script>
import FileInput from '@/components/FileInput.vue'
export default {
  components: { FileInput },
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    tokensLength: {
      type: Number,
      default: 0
    }
  },
  emits: {
    analyzeCode: null,
    updateCode: null,
    clearTokens: null,
    clearTokensSnckbar: null
  },
  data() {
    return {
      code: '',
      line: 1,
      column: 1
    }
  },
  watch: {
    code: {
      immediate: true,
      handler(val) {
        this.$emit('updateCode', val)
      }
    }
  },
  mounted() {
    // Sincronizar el scroll del editor con el scroll del code-block
    const editor = this.$refs.editor
    const codeBlock = this.$refs.codeBlock

    editor.addEventListener('scroll', function () {
      // Para scroll vertical
      const editorScrollTop = editor.scrollTop
      codeBlock.scrollTop = editorScrollTop

      // Para scroll horizontal
      // const editorScrollLeft = editor.scrollLeft
      // codeBlock.scrollLeft = editorScrollLeft
    })
  },
  unmounted() {
    // Eliminar el event-listener del scroll
    const editor = document.getElementById('editor')
    editor.removeEventListener('scroll', function () {})
  },
  methods: {
    clearCode() {
      this.code = ''
      this.handleInput()
    },
    readTextFromFile(content) {
      this.code = content
      this.handleInput()
    },
    analyzeCode() {
      this.$emit('analyzeCode', this.code)
    },
    handleInput() {
      this.calculateCursorPosition()
      if (this.tokensLength > 0) {
        this.$emit('clearTokensSnckbar', {
          text: 'Se ha modificado el código, se han eliminado los tokens',
          details: 'Para ver los tokens nuevos, analiza el código nuevamente',
          type: 'warn'
        })
      }
      this.$emit('clearTokens')
    },
    handleKeyDown() {
      // Se "retarda" la ejecución para que el DOM se actualice correctamente y no sea retrasado el cambio de cursor
      setTimeout(() => {
        this.calculateCursorPosition()
      }, 0)
    },
    calculateCursorPosition() {
      const position = this.$refs.editor.selectionStart
      const value = this.code.substring(0, position)
      const lineBreaks = value.match(/\n/g)
      this.line = lineBreaks ? lineBreaks.length + 1 : 1
      this.column = value.length - value.lastIndexOf('\n', position - 1)
    }
  }
}
</script>
<style scoped>
.cursor-position-info {
  font-weight: bold;
}

.code-container {
  width: 60%;
  height: 800px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1rem;
}
.code-actions {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;
  margin-top: 1rem;
}

.code-area {
  position: relative;
  width: 100%;
  height: 650px;
  border-radius: 5px;
  border: 1px solid #ccc;
  /* overflow: hidden; */
}
.code-input {
  border-radius: 5px;
  padding: 10px;
  font-family: 'JetBrains Mono', monospace;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: transparent;
  caret-color: black;
  z-index: 1;
  border: none;
  resize: none;
  color: transparent;
  white-space: pre-wrap;
  outline: 2px solid transparent;
  transition:
    outline 0.2s ease-in-out,
    box-shadow 0.2s ease-in-out;
}

.code-input:focus {
  outline: 2px solid #bebebe;
  box-shadow:
    0px 4px 6px -1px rgba(0, 0, 0, 0.1),
    0px 2px 4px -1px rgba(0, 0, 0, 0.06);
}
.code-display {
  border-radius: 5px;
  padding: 10px;
  font-family: 'JetBrains Mono', monospace;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  white-space: pre-wrap;
  background-color: #f4f4f4;
  overflow: auto;
}

/* .code-input,
.code-display {
  padding: 1rem;
  position: absolute;
  top: 0;
  left: 0;
  border-radius: 5px;
  font-family: 'JetBrains Mono', monospace;
  font-weight: 600;
} */
</style>
