<template>
  <header class="header">
    <h2 class="title">Analizador léxico ∙ <code>parser-py</code> ∙ Lenguajes Formales SS 2023</h2>
    <ConfigurationMenu
      @update-show-new-lines="showNewLines = $event"
      @update-show-spaces="showSpaces = $event"
    />
  </header>
  <section class="main-section">
    <TextEditor
      :loading="loading"
      :tokens="tokens.tokensFound"
      :tokens-length="tokensLength"
      @update-code="code = $event"
      @analyze-code="analyze"
      @clear-tokens="tokens = []"
    />
    <div class="table-area">
      <TokensPanels
        :tokens-main="tokens"
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
import TextEditor from '@/components/TextEditor.vue'
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
      return this.tokens?.tokensFound?.length
    }
  },
  methods: {
    analyze() {
      this.loading = true
      fetch(`${API_BASE_URL}/syntax`, {
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
  grid-template-columns: 1fr auto;
  align-items: center;
  justify-content: space-between;
  padding: 0rem 1rem;
}

header h2 {
  text-align: center;
  font-weight: 900;
}
</style>
