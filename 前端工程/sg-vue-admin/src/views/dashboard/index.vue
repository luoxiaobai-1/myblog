<template>
  <div class="dashboard-container">
 
    <div class="top">
			<div class="top-left">
				<h1>欢迎来到博客后台管理系统</h1>
				
      </div>
     
		</div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 0;
  }
  &-text {
    font-size: 30px;
    // line-height: 46px;
  }
  
}
.dashboard-container {
  background-image:url('../../assets/images/cool-background\ \(2\).svg');
height: 575px;
width: 660 px;


  }		
  h1 ,h2{
    top: 90px;
    writing-mode:vertical-lr;
    color: rgb(122, 168, 221);
    margin-left: 20px;
    font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;

    
  }
  
</style>
