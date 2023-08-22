<template>
  <header class="header">
    <h1 class="title">Analizador léxico <code>parser-py</code> - Lenguajes Formales SS 2023</h1>
    <ConfigurationMenu
      @update-show-new-lines="showNewLines = $event"
      @update-show-spaces="showSpaces = $event"
    />
  </header>
  <section class="main-section">
    <TextEditor
      :loading="loading"
      :tokens="tokens"
      :tokens-length="tokensLength"
      @update-code="code = $event"
      @analyze-code="analyze"
      @clear-tokens="tokens = []"
    />
    <div class="table-area">
      <TokensPanels
        :tokens="tokens"
        :loading="loading"
        :show-spaces="showSpaces"
        :show-new-lines="showNewLines"
      />
    </div>
  </section>
  <GeneralSnackbar ref="snackbar" :info="snackbarInfo" />
</template>

<script>
import { API_BASE_URL } from '../utils/constants.js'
import GeneralSnackbar from '@/components/GeneralSnackbar.vue'
import TextEditor from '../components/TextEditor.vue'
import TokensPanels from '@/components/TokensPanels.vue'
import ConfigurationMenu from '@/components/ConfigurationMenu.vue'
export default {
  components: {
    TextEditor,
    GeneralSnackbar,
    TokensPanels,
    ConfigurationMenu
  },
  data() {
    return {
      menu: false,
      showSpaces: true,
      showNewLines: true,
      code: '',
      loading: false,
      tokens: [],
      snackbarInfo: {
        text: '',
        type: '',
        details: ''
      }
    }
  },
  computed: {
    tokensLength() {
      return this.tokens.length
    }
  },
  methods: {
    analyze() {
      this.loading = true
      fetch(`${API_BASE_URL}/analyze`, {
        method: 'POST',
        headers: {
          'Content-Type': 'text/plain'
        },
        body: this.code
      })
        .then((response) => response.json())
        .then((data) => {
          this.tokens = data
        })
        .catch((error) => {
          console.error(error)
          this.snackbarInfo = {
            text: 'Ocurrió un error al analizar el código, puede que haya un problema con el servidor',
            details: error.message,
            type: 'error'
          }
          this.$refs.snackbar.show()
        })
        .finally(() => {
          this.loading = false
        })
    }
  }
}
</script>

<style scoped>
code {
  font-family: 'JetBrains Mono', monospace;
}
.main-section {
  max-width: 1900px;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 2rem;
  padding: 1rem;
}

.table-area {
  width: 50%;
  max-height: 800px;
}
.header {
  display: grid;
  grid-template-columns: 1fr auto; /* One column for title, one for icon */
  align-items: center;
  justify-content: space-between;
}

header h1 {
  text-align: center;
}
</style>
