<template>
  <v-data-table
    :headers="headers"
    :items="tokens"
    class="pb-4 rounded"
    hide-default-footer
    :loading="loading"
    :loading-text="'Analizando código...'"
    :no-data-text="noDataText"
    :items-per-page="5"
    :items-per-page-options="[
      { value: 5, title: '5' },
      { value: 10, title: '10' },
      { value: -1, title: '$vuetify.dataFooter.itemsPerPageAll' }
    ]"
    density="comfortable"
    height="350px"
    width="100px"
  >
    <template #item.lexeme="{ item }">
      <code class="table-lexeme">
        {{ item.columns.lexeme }}
      </code>
    </template>
    <template #item.pattern="{ item }">
      <span class="table-pattern">
        {{ item.columns.pattern }}
      </span>
    </template>
    <template #item.type="{ item }">
      <span class="table-type">
        {{ item.columns.type }}
      </span>
    </template>
  </v-data-table>
</template>
<script>
export default {
  props: {
    tokens: {
      type: Array,
      default: () => []
    },
    loading: {
      type: Boolean,
      default: false
    },
    noDataText: {
      type: String,
      default: 'No se han encontrado tokens en el código.'
    },
    showPattern: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {}
  },
  computed: {
    headers() {
      const normalHeaders = [
        { title: 'Lexema', key: 'lexeme' },
        { title: 'Tipo', key: 'type' },
        { title: 'Patrón', key: 'pattern' },
        { width: '10', title: 'Linea', key: 'line' },
        { width: '10px', title: 'Columna', key: 'column' }
      ]
      if (!this.showPattern) {
        normalHeaders.splice(2, 1)
      }

      return normalHeaders
    }
  }
}
</script>
<style scoped>
.table-lexeme {
  /* padding: 2px; */
  padding-left: 5px;
  padding-right: 5px;
  background-color: #dfdfdf;
  font-family: 'JetBrains Mono', monospace;
  font-size: 0.8rem;
  font-weight: 600;
}

.table-pattern {
  /* padding: 2px; */
  /* padding-left: 5px;
  padding-right: 5px;
  background-color: #dfdfdf;
  font-weight: 600; */
  font-family: 'JetBrains Mono', monospace;
  font-size: 0.8rem;
}

.table-type {
  font-size: 0.8rem;
}
</style>
