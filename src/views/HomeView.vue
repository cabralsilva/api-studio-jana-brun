<template>
  <v-container id="dashboard" fluid tag="section">
    <v-row class="mt-1">
      <v-col cols="12" sm="6" lg="4">
        <base-material-stats-card
          color="primary"
          icon="mdi-human-female-dance"
          title="Total de alunos"
          value="184"
        />
      </v-col>
      <v-col cols="12" sm="6" lg="4">
        <base-material-stats-card
          color="accent"
          icon="mdi-calendar"
          title="Espetáculo"
          value="Faltam 154 dias"
        />
      </v-col>
      <v-col cols="12" sm="6" lg="4">
        <base-material-stats-card
          color="secondary"
          icon="mdi-party-popper"
          title="Aniversiantes de hoje"
          value="02/05/2022">
          <v-timeline slot="contentCardCustom" dense align-top>
            <v-timeline-item class="mb-1" color="primary" icon-color="grey lighten-2" small>
              <v-row class="pt-1">
                <v-col>
                  <strong>Rafaela Campos</strong>
                  <div class="text-caption">
                    Responsável pela Aline Campos
                  </div>
                  <v-avatar>
                    <v-img
                      src="https://avataaars.io/?avatarStyle=Circle&topType=LongHairFrida&accessoriesType=Kurt&hairColor=Red&facialHairType=BeardLight&facialHairColor=BrownDark&clotheType=GraphicShirt&clotheColor=Gray01&graphicType=Skull&eyeType=Wink&eyebrowType=RaisedExcitedNatural&mouthType=Disbelief&skinColor=Brown"
                    ></v-img>
                  </v-avatar>
                </v-col>
              </v-row>
            </v-timeline-item>
            <v-timeline-item class="mb-1" color="primary" icon-color="grey lighten-2" small>
              <v-row class="pt-1">
                <v-col>
                  <strong>Jonathan Pierin</strong>
                  <div class="text-caption">
                    Diretor financeiro
                  </div>
                  <v-avatar>
                    <v-img
                      src="https://avataaars.io/?avatarStyle=Circle&topType=ShortHairFrizzle&accessoriesType=Prescription02&hairColor=Black&facialHairType=MoustacheMagnum&facialHairColor=BrownDark&clotheType=BlazerSweater&clotheColor=Black&eyeType=Default&eyebrowType=FlatNatural&mouthType=Default&skinColor=Tanned"
                    ></v-img>
                  </v-avatar>
                </v-col>
              </v-row>
            </v-timeline-item>
          </v-timeline>
        </base-material-stats-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import Vue from 'vue'
import BaseMaterialStatsCard from '@/components/dashboard/MaterialStatsCard.vue'

export default Vue.extend({
  components: {
    BaseMaterialStatsCard
  }
})
</script>
