<template>
  <v-expansion-panels v-model="panel" mandatory>
    <v-expansion-panel value="valid">
      <v-expansion-panel-title>
        <v-icon class="mr-3"> mdi-code-tags-check </v-icon>
        <h3><strong>Tokens validos encontrados:</strong></h3>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="mx-n6">
        <TokensTable :tokens="filteredData" :loading="loading" />
      </v-expansion-panel-text>
    </v-expansion-panel>
    <v-expansion-panel value="invalid">
      <v-expansion-panel-title>
        <v-icon class="mr-3"> mdi-progress-alert </v-icon>
        <h3><strong>Errores / Tokens invalidos:</strong></h3>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="mx-n6">
        <TokensTable
          :tokens="errorTokens"
          :loading="loading"
          :show-pattern="false"
          no-data-text="No se han encontrado errores"
        />
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
import TokensTable from '@/components/TokensTable.vue'
export default {
  components: {
    TokensTable
  },
  props: {
    tokens: {
      type: Array,
      default: () => []
    },
    loading: {
      type: Boolean,
      default: false
    },
    showSpaces: {
      type: Boolean,
      default: true
    },
    showNewLines: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      panel: ['valid']
    }
  },
  computed: {
    filteredData() {
      const newTokens = this.tokens.filter((token) => token.type !== 'INVALID_UNIDENTIFIED')
      return newTokens.filter((item) => {
        if (this.showSpaces && this.showNewLines) {
          console.log('No filter')
          return true
        } else if (!this.showSpaces && this.showNewLines) {
          console.log('No spaces')
          return item.type !== 'SPACE'
        } else if (this.showSpaces && !this.showNewLines) {
          console.log('No new lines')
          return item.type !== 'NEW_LINE'
        } else if (!this.showSpaces && !this.showNewLines) {
          console.log('No spaces and no new lines')
          return item.type !== 'SPACE' && item.type !== 'NEW_LINE'
        } else {
          return true
        }
      })
    },
    validTokens() {
      return this.tokens.filter((token) => token.type !== 'INVALID_UNIDENTIFIED')
    },
    errorTokens() {
      return this.tokens.filter((token) => token.type === 'INVALID_UNIDENTIFIED')
    }
  }
}
</script>
<style scoped></style>
