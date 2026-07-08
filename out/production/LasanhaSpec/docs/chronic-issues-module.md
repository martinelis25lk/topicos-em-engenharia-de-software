# Chronic Issues Module

## Purpose

Collaborative automotive chronic issue reporting system.

Users can:

- create chronic issues
- vote on usefulness
- report real occurrences on their own vehicles

---

## Business Rules

### Voting

A user can vote only once per issue.

Cases:

- first vote → creates IssueVote + increments counter
- same vote again → ignored (idempotent)
- changing vote → decrements old counter + increments new one

Status transition:

- if usefulVotes >= 10
- and issue status == PENDING
- then issue becomes IN_REVIEW

Validation:

- issue must exist
- user must exist

---

### Occurrence Reporting

Users can report real occurrences of a chronic issue.

Required payload:

- vehicleId
- millageAtOccurrence
- repairCost
- description

Validation:

- issue must exist
- vehicle must exist
- vehicle model must match issue model
- duplicate occurrence for same vehicle is forbidden

Flow:

POST /chronic-issues/{issueId}/occurrence

---

## Known Limitations

Current technical debt:

- userId still comes from request body (should come from JWT)
- occurrence username returns null
- IssueOcurrence typo should be renamed
- counters are denormalized aggregates (possible race conditions)