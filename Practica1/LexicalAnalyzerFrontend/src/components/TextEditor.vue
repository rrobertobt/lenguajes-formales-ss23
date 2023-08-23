<template>
  <div class="code-container">
    <h4 class="mb-n2">Escribe el codigo aqui:</h4>
    <span>
      <v-chip
        class="mr-3"
        color="pink-accent-3"
        prepend-icon="mdi-backspace-outline"
        variant="outlined"
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
        @scroll="syncScrollCodeBlock"
      ></textarea>
      <ColoredOutput :scroll-top="scrollTop" :tokens="tokens" :code="code" />
    </div>
    <div class="code-actions">
      <FileInput @file-read="readTextFromFile($event)" />
      <v-tooltip
        text="Esta aplicación analiza el código mientras escribes, pero si no funciona correctamente, puedes usar este botón"
        location="top"
        max-width="300"
        open-delay="600"
      >
        <template #activator="{ props }">
          <v-btn
            v-bind="props"
            size="large"
            :loading="loading"
            color="deep-purple-accent-3"
            @click="analyzeCode"
          >
            <v-icon class="mx-2" size="large"> mdi-cog-play-outline </v-icon>
            <strong>Analizar</strong>
          </v-btn>
        </template>
      </v-tooltip>
    </div>
  </div>
</template>
<script>
import FileInput from '@/components/FileInput.vue'
import ColoredOutput from '@/components/ColoredOutput.vue'
export default {
  components: { FileInput, ColoredOutput },
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    tokensLength: {
      type: Number,
      default: 0
    },
    tokens: {
      type: Array,
      default: () => []
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
      column: 1,
      scrollTop: 0
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
  methods: {
    syncScrollCodeBlock(event) {
      this.scrollTop = event.target.scrollTop
    },
    clearCode() {
      this.code = ''
      this.$emit('clearTokens')
    },
    readTextFromFile(content) {
      this.code = content
      this.$emit('updateCode', content)
      this.analyzeCode()
    },
    analyzeCode() {
      this.$emit('analyzeCode')
    },
    handleInput() {
      this.calculateCursorPosition()
      this.analyzeCode()
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
  outline: 1.5px solid transparent;
  transition:
    outline 0.2s ease-in-out,
    box-shadow 0.2s ease-in-out;
}

.code-input:focus {
  outline: 1.5px solid #b39ddb;
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
  color: #000;
  left: 0;
  width: 100%;
  height: 100%;
  white-space: pre-wrap;
  background-color: #f4f4f4;
  overflow: auto;
}
</style>
