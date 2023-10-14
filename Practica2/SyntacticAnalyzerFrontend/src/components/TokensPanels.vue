<template>
  <v-expansion-panels v-model="panel" mandatory>
    <v-expansion-panel value="1">
      <v-expansion-panel-title>
        <v-icon class="mr-3"> mdi-code-tags-check </v-icon>
        <h3><strong>Tabla de simbolos de sintaxis</strong></h3>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="mx-n6">
        <SymbolsTableSyntax :symbols-found="tokens" />
      </v-expansion-panel-text>
    </v-expansion-panel>
    <v-expansion-panel value="2">
      <v-expansion-panel-title>
        <v-icon class="mr-3"> mdi-code-tags-check </v-icon>
        <h3 class="mr-8"><strong>Tabla de llamadas a metodos</strong></h3>
        <span>
          Total de métodos encontrados: <strong>{{ methodsCalled?.totalMethodsCalled }}</strong>
        </span>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="mx-n6">
        <MethodsCallsTable :methods-calls-table="methodsCalled" />
      </v-expansion-panel-text>
    </v-expansion-panel>
    <v-expansion-panel value="3">
      <v-expansion-panel-title>
        <v-icon class="mr-3"> mdi-progress-alert </v-icon>
        <h3><strong>Tabla de errores de sintaxis</strong></h3>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="mx-n6">
        <ErrorsTableSyntax :errors-found="errors" />
      </v-expansion-panel-text>
    </v-expansion-panel>
    <v-expansion-panel value="4">
      <v-expansion-panel-title>
        <v-icon class="mr-3"> mdi-progress-alert </v-icon>
        <h3><strong>Errores / Tokens invalidos:</strong></h3>
      </v-expansion-panel-title>
      <v-expansion-panel-text class="mx-n6">
        <TokensTable
          :tokens="errorTokens"
          :loading="loading"
          :show-pattern="false"
          no-data-text="No se han encontrado tokens invalidos"
        />
      </v-expansion-panel-text>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script>
import TokensTable from '@/components/TokensTable.vue'
import SymbolsTableSyntax from '@/components/SymbolsTableSyntax.vue'
import ErrorsTableSyntax from '@/components/ErrorsTableSyntax.vue'
import MethodsCallsTable from '@/components/MethodsCallsTable.vue'
export default {
  components: {
    TokensTable,
    SymbolsTableSyntax,
    ErrorsTableSyntax,
    MethodsCallsTable
  },
  props: {
    tokensMain: {
      type: Object,
      default: () => {}
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
      panel: ['1']
    }
  },
  computed: {
    tokens() {
      return this.tokensMain?.syntaxSymbolTable?.symbolTableItems
    },
    errors() {
      return this.tokensMain?.errorsTable?.errorsTableItems
    },
    methodsCalled() {
      return this.tokensMain?.methodCallsTable
    },
    filteredData() {
      const newTokens = this.tokens?.filter((token) => token.type !== 'INVALID_UNIDENTIFIED')
      return newTokens.filter((item) => {
        if (this.showSpaces && this.showNewLines) {
          return true
        } else if (!this.showSpaces && this.showNewLines) {
          return item.type !== 'SPACE'
        } else if (this.showSpaces && !this.showNewLines) {
          return item.type !== 'NEW_LINE'
        } else if (!this.showSpaces && !this.showNewLines) {
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
      return this.tokensMain?.tokensFound?.filter((token) => token.type === 'INVALID_UNIDENTIFIED')
    }
  }
}
</script>
<style scoped></style>
