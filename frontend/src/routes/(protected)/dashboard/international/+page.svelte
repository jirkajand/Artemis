	<script lang="ts">
		import Card, { Content as CardContent } from '@smui/card';
		import LayoutGrid, { Cell as LayoutGridCell } from '@smui/layout-grid';
		import List, { Item as ListItem, Text as ListText } from '@smui/list';
		import ProfilePicture from '$lib/components/profile/ProfilePicture.svelte';

		let { data } = $props();
		const { student } = data;
		const { assignedBuddy } = $derived(student);
		console.log(assignedBuddy, student);
	</script>

	<LayoutGrid>
		<LayoutGridCell span="4">
			<Card>
				<CardContent>
					<h2>You</h2>
					<ProfilePicture displayBadges={false} profilePicture={student?.profilePicture} />
					<List two-line>
						<ListItem>
							<ListText>
								<span><b>Name:</b> {student?.firstName} {student?.lastName}</span>
							</ListText>
						</ListItem>
						<ListItem>
							<ListText>
								<span><b>Email:</b> {student?.email}</span>
							</ListText>
						</ListItem>
						<ListItem>
							<ListText>
								<span><b>Phone number:</b> {student?.phoneNumber}</span>
							</ListText>
						</ListItem>
						<ListItem>
							<ListText>
								<span><b>Faculty:</b> {student?.faculty?.shortName}</span>
							</ListText>
						</ListItem>

					</List>
				</CardContent>
			</Card>
		</LayoutGridCell>

		<LayoutGridCell span="4" class="handshake-cell">
			<Card variant="outlined" style="text-align: center;">
				<CardContent>
					{#if assignedBuddy?.id}
						<span style="font-size: 48px;">🤝</span>
						<h3 class="assigned-heading">You have a buddy assigned!</h3>
						<p>You have been matched with <b>{assignedBuddy?.firstName}</b></p>
						<p>Let's get you connected!</p>
					{:else}
						<span style="font-size: 48px;">🔎</span>
						<h3>Looking for a Buddy...</h3>
						<p>No buddy assigned yet. Check back soon!</p>
					{/if}
				</CardContent>
			</Card>
		</LayoutGridCell>

		<LayoutGridCell span="4">
			<Card>
				<CardContent>
					<h2>Your Buddy</h2>
						<ProfilePicture displayBadges={false} profilePicture={assignedBuddy?.profilePicture} />
					{#if assignedBuddy?.id}
						<List two-line>
							<ListItem>
								<ListText>
									<span><b>Name:</b> {assignedBuddy?.firstName} {assignedBuddy?.lastName}</span>
								</ListText>
							</ListItem>
							<ListItem>
								<ListText>
									<span><b>Email:</b> {assignedBuddy?.email}</span>
								</ListText>
							</ListItem>
							<ListItem>
								<ListText>
									<span><b>Phone number:</b> {assignedBuddy?.phoneNumber}</span>
								</ListText>
							</ListItem>
							<ListItem>
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

		:global(.mdc-layout-grid) {
			height: 100%;
		}

		:global(.mdc-layout-grid__inner) {
			height: 100%;
		}

		:global(.mdc-card--outlined) {
			height: 50% !important;
			justify-content: center;
		}

		:global(.mdc-card--outlined .smui-card__content) {
			justify-content: center;

			p {
				margin: 0.5rem 0;
			}

			h3 {
				font-size: calc(0.35rem + 1vw) ;
			}

			span {
				font-size: 60px !important;
			}
		}

		:global(.mdc-layout-grid__cell) {
			height: 100%;
			align-content: center;
			justify-content: space-around;
		}

		:global(.smui-card__content) {
			flex: 1;
			padding: 1.5rem;
			justify-content: space-between;
			text-align: center;
		}

		:global(.mdc-layout-grid__cell:nth-child(odd) .mdc-card) {
			background-color: var(--highlight-bg);
			min-height: 65%;
		}

		:global(.mdc-layout-grid__cell .mdc-card) {
			border-radius: 10%;
		}


		:global(.image-wrapper) {
			align-self: center;
			max-width: 12.5vw !important;
		}

		:global(.smui-card__content) {
			display: flex;
			flex-direction: column;
		}

		:global(.mdc-deprecated-list-item){
			max-height: 35px;
		}



	</style>