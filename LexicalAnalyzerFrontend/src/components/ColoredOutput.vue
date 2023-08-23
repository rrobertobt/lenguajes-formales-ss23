<template>
  <pre
    v-if="tokens.length"
    ref="codeBlock"
    class="code-display"
  ><span v-for="(token, index) in tokens" :key="index" :style="`color: ${getTokenColor(token.type)};`">{{ token.lexeme }}</span>
  </pre>
  <!-- Fallback en caso no se pueda colorear correctamente el código -->
  <pre v-else ref="codeBlock" class="code-display"
    >{{ code }}
  </pre>
</template>
<script>
export default {
  props: {
    tokens: {
      type: Array,
      default: () => []
    },
    code: {
      type: String,
      default: ''
    },
    scrollTop: {
      type: Number,
      default: 0
    }
  },
  watch: {
    scrollTop: {
      immediate: true,
      handler(val) {
        const codeBlock = this.$refs.codeBlock
        if (codeBlock) {
          codeBlock.scrollTop = val
        }
      }
    }
  },
  methods: {
    getTokenColor(type) {
      switch (type) {
        case 'IDENTIFIER':
          return '#000000'
        case 'KEYWORD':
          // Morado
          return '#9c27b0'
        case 'COMMENT':
          // Gris
          return '#9e9e9e'
        // Constantes (numericas y cadenas)
        // Naranja
        case 'STRING':
        case 'NUMERIC_CONSTANT_WHOLE':
        case 'NUMERIC_CONSTANT_DECIMAL':
          return '#ff9800'
        // Operadores (aritmeticos, comparación, lógicos y asignación)
        // Celeste
        case 'OPERATOR_ARITHMETIC':
        case 'OPERATOR_COMPARISON':
        case 'OPERATOR_LOGIC':
        case 'OPERATOR_ASSIGNMENT':
          return '#03a9f4'
        default:
          // Verde
          return '#4caf50'
      }
    }
  }
}
</script>

<style scoped>
pre > span {
  font-family: 'JetBrains Mono', monospace;
}
</style>
