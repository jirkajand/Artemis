<script lang="ts">
	import Card, { Content as CardContent } from '@smui/card';
	import LayoutGrid, { Cell as LayoutGridCell } from '@smui/layout-grid';
	import List, { Item as ListItem, Text as ListText } from '@smui/list';
	import ProfilePicture from '$lib/components/profile/ProfilePicture.svelte';

	let { data } = $props();
	let student = $derived(data.student);
	let assignedBuddy = $derived(student?.assignedBuddy);

	$effect(() => {
		console.log(assignedBuddy, student);
	});
</script>

<LayoutGrid class="buddy-grid">
	<LayoutGridCell span="4" class="card-column left-column">
		<Card class="profile-card your-profile-card">
			<CardContent class="card-content-wrapper">
				<h2>You</h2>
				<ProfilePicture displayBadges={false} profilePicture={student?.profilePicture} />
				<List class="profile-list">
					<ListItem class="profile-list-item">
						<ListText>
							<span><b>Name:</b> {student?.firstName} {student?.lastName}</span>
						</ListText>
					</ListItem>
					<ListItem class="profile-list-item">
						<ListText>
							<span><b>Email:</b> {student?.email}</span>
						</ListText>
					</ListItem>
					<ListItem class="profile-list-item">
						<ListText>
							<span><b>Phone number:</b> {student?.phoneNumber}</span>
						</ListText>
					</ListItem>
					<ListItem class="profile-list-item">
						<ListText>
							<span><b>Faculty:</b> {student?.faculty?.shortName}</span>
						</ListText>
					</ListItem>
				</List>
			</CardContent>
		</Card>
	</LayoutGridCell>

	<LayoutGridCell span="4" class="card-column buddy-handshake-column">
		<Card variant="outlined" class="buddy-handshake-card">
			<CardContent class="handshake-content">
				{#if assignedBuddy?.id}
					<span class="handshake-icon">🤝</span>
					<h3 class="assigned-heading">You have a buddy assigned!</h3>
					<p>You have been matched with <b>{assignedBuddy?.firstName}</b></p>
					<p>Let's get you connected!</p>
				{:else}
					<span class="handshake-icon">🔎</span>
					<h3>Looking for a Buddy...</h3>
					<p>No buddy assigned yet. Check back soon!</p>
				{/if}
			</CardContent>
		</Card>
	</LayoutGridCell>

	<LayoutGridCell span="4" class="card-column right-column">
		<Card class="profile-card buddy-profile-card">
			<CardContent class="card-content-wrapper">
				<h2>Your Buddy</h2>
				<ProfilePicture displayBadges={false} profilePicture={assignedBuddy?.profilePicture} />
				{#if assignedBuddy?.id}
					<List class="profile-list">
						<ListItem class="profile-list-item">
							<ListText>
								<span><b>Name:</b> {assignedBuddy?.firstName} {assignedBuddy?.lastName}</span>
							</ListText>
						</ListItem>
						<ListItem class="profile-list-item">
							<ListText>
								<span><b>Email:</b> {assignedBuddy?.email}</span>
							</ListText>
						</ListItem>
						<ListItem class="profile-list-item">
							<ListText>
								<span><b>Phone number:</b> {assignedBuddy?.phoneNumber}</span>
							</ListText>
						</ListItem>
						<ListItem class="profile-list-item">
							<ListText>
								<span><b>Faculty:</b> {assignedBuddy?.faculty?.shortName}</span>
							</ListText>
						</ListItem>
					</List>
				{:else}
					<p>Waiting for a local student to pick you up!</p>
				{/if}
			</CardContent>
		</Card>
	</LayoutGridCell>
</LayoutGrid>

<style lang="scss">

  :global(main) {
    max-width: unset !important;
    height: 100%;
  }


  :global(.buddy-grid) {
    height: 100%;

    :global(.mdc-layout-grid__inner) {
      height: 100%;
    }
  }


  :global(.card-column) {
    height: 100%;
    align-content: center;
    justify-content: space-around;
  }


  :global(.profile-card) {
    border-radius: 10%;
    min-height: 65%;
  }


  :global(.left-column .profile-card) {
    background-color: var(--highlight-bg);
  }


  :global(.card-content-wrapper) {
    display: flex;
    flex-direction: column;
    flex: 1;
    padding: 1.5rem;
    justify-content: space-between;
    text-align: center;
  }


  :global(.profile-list-item) {
    max-height: 35px;
  }


  :global(.profile-card .image-wrapper) {
    align-self: center;
    max-width: 12.5vw !important;
  }


  :global(.buddy-handshake-card) {
    height: 50% !important;
    justify-content: center;
    border-radius: 10%;
    text-align: center;
  }


  :global(.handshake-content) {
    justify-content: center;


    p {
      margin: 0.5rem 0;
    }

    h3 {
      font-size: calc(0.35rem + 1vw);
    }
  }


  .handshake-icon {
    font-size: 60px !important;
  }
</style>