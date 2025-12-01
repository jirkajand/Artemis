<script lang="ts">
    let { change, accept, multiple, description } = $props();
    let dragging = $state(false);
    let fileNames = $state<string[]>([]);

    function handleChange(event: Event) {
        dragging = false;
        const files = (event.target as HTMLInputElement).files;
        if (!files || !files[0]) return;
        
        fileNames = Array.from(files).map(file => file.name);
        change(files);
    }
</script>

<span class={`dragBox ${dragging? "dragging": ""}`} >
    {#if fileNames.length > 0}
        {fileNames.join(', ')}
    {:else}
        {description}
    {/if}
    <input 
        type="file" 
        accept={accept} 
        multiple={multiple} 
        ondragenter={()=>{dragging = true}} 
        ondragleave={()=>{dragging = false}} 
        onchange={handleChange}
    />
</span>

<style>
.dragBox {
    width: 250px;
    height: 100px;
    margin: 0 auto;
    position: relative;
    text-align: center;
    font-weight: bold;
    line-height: 95px;
    color: var(--on-nav-bg);
    border: 2px dashed var(--on-nav-bg);
    background: var(--neutral-bg);
    display: inline-block;
    transition: 0.1s;
}
.dragBox:hover{
    border-color: var(--primary);
}
.dragging {
    transform: scale(1.1);
    border-color: var(--primary);
    background: var(--secondary);
}
input[type="file"] {
    position: absolute;
    height: 100%;
    width: 100%;    
    opacity: 0;
    top: 0;
    left: 0;
}
</style>
