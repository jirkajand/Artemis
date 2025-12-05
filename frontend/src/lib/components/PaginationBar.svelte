<script lang="ts">
	let {
		total,
		perPage = 20,
		page = $bindable(1),
		onPageChange
	} = $props();

	let totalPages = $derived(Math.ceil(total / perPage));

	function handlePageChange(newPage: number) {
		if (newPage >= 1 && newPage <= totalPages) {
			page = newPage;
			onPageChange?.(newPage);
		}
	}
</script>

<nav aria-label="Pagination">
	<button
		disabled={page === 1}
		onclick={() => handlePageChange(page - 1)}
		class="page-btn"
	>
		Previous
	</button>

	{#each Array(totalPages) as _, i}
		{@const pageNum = i + 1}
		<button
			aria-current={page === pageNum ? 'page' : undefined}
			class="page-btn"
			class:active={page === pageNum}
			onclick={() => handlePageChange(pageNum)}
		>
			{pageNum}
		</button>
	{/each}

	<button
		disabled={page === totalPages}
		onclick={() => handlePageChange(page + 1)}
		class="page-btn"
	>
		Next
	</button>
</nav>

<style>
    nav {
        display: flex;
        gap: 0.5rem;
        align-items: center;
        flex-wrap: wrap;
        justify-content: center;
    }

    .page-btn {
        padding: 0.5rem 1rem;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        border-radius: 4px;
        min-width: 40px;
    }

    .page-btn:disabled {
        opacity: 0.5;
        cursor: not-allowed;
    }

    .active {
        background: #007bff;
        color: white;
        border-color: #007bff;
    }
</style>
