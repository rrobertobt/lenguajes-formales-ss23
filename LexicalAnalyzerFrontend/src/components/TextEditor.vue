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
    <div class="code-area elevation-3">
      <textarea
        id="editor"
        ref="editor"
        v-model="code"
        class="code-input"
        name=""
        cols="30"
        rows="10"
        spellcheck="false"
        @input="handleInput"
        @keydown="handleKeyDown"
        @click="calculateCursorPosition"
      ></textarea>

      <pre id="code-block" class="code-display"
        >{{ code }}
        </pre
      >
    </div>
    <div class="code-actions">
      <v-btn size="large" :loading="loading" color="amber-darken-2" @click="analyzeCode">
        <v-icon class="mx-2" size="large"> mdi-cog-play-outline </v-icon>
        <strong>Analizar</strong>
      </v-btn>
    </div>
  </div>
</template>
<script>
export default {
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
    clearTokens: null
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
    const editor = document.getElementById('editor')
    const codeBlock = document.getElementById('code-block')

    editor.addEventListener('scroll', function () {
      // Para scroll vertical
      const editorScrollTop = editor.scrollTop
      codeBlock.scrollTop = editorScrollTop
      // Para scroll horizontal
      const editorScrollLeft = editor.scrollLeft
      codeBlock.scrollLeft = editorScrollLeft
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
    analyzeCode() {
      this.$emit('analyzeCode', this.code)
    },
    handleInput() {
      this.calculateCursorPosition()
      if (this.tokensLength > 0) {
        // TODO: notificar al usuario que se detectó un cambio en el código y se limpiará la tabla
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
}

.code-container {
  width: 60%;
  height: 800px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 1.1rem;
}

.code-display {
  padding: 1rem;
  z-index: 0;
  width: 100%;
  background-color: #1e1e1e;
  color: white;
  overflow: auto;
}

.code-input {
  z-index: 1;
  color: transparent;
  caret-color: white;
  width: 100%;
  height: 100%;
  border: none;
  outline: none;
  resize: none;
  padding: 1rem;
  /* border-radius: 2rem; */
  border: 1px solid #000000;
}

.code-input,
.code-display {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  white-space: pre-wrap;
  border-radius: 5px;
  font-family: 'JetBrains Mono', monospace;
  font-weight: 600;
}
</style>
