<template>
  <v-menu v-model="menu" location="end" :close-on-content-click="false">
    <template #activator="{ props }">
      <v-btn icon v-bind="props" :color="buttonColor">
        <v-icon>
          {{ menuIcon }}
        </v-icon>
      </v-btn>
    </template>
    <v-card min-width="300" class="rounded-xl">
      <v-list>
        <v-list-item>
          <strong>Configuración</strong>
        </v-list-item>
      </v-list>
      <v-divider></v-divider>
      <v-list>
        <v-list-item>
          <v-list-item-title
            >¿Mostrar tokens de <code>Espacio</code> y <code>Nueva Linea</code> en el
            reporte?</v-list-item-title
          >
        </v-list-item>
        <v-list-item>
          <v-switch
            v-model="showSpaces"
            color="deep-purple-accent-2"
            label="Espacios"
            hide-details
            inset
          ></v-switch>
        </v-list-item>
        <v-list-item>
          <v-switch
            v-model="showNewLines"
            color="deep-purple-accent-2"
            label="Nueva línea"
            hide-details
            inset
          ></v-switch>
        </v-list-item>
      </v-list>
    </v-card>
  </v-menu>
</template>
<script>
export default {
  emits: { updateShowSpaces: null, updateShowNewLines: null },
  data() {
    return {
      menu: false,
      showSpaces: true,
      showNewLines: true
    }
  },
  computed: {
    menuIcon() {
      return this.menu ? 'mdi-close' : 'mdi-cog-outline'
    },
    buttonColor() {
      return this.menu ? 'pink-accent-3' : 'black'
    }
  },
  watch: {
    showSpaces: {
      handler() {
        this.$emit('updateShowSpaces', this.showSpaces)
      },
      immediate: true
    },
    showNewLines: {
      handler() {
        this.$emit('updateShowNewLines', this.showNewLines)
      },
      immediate: true
    }
  }
}
</script>
<style lang=""></style>
