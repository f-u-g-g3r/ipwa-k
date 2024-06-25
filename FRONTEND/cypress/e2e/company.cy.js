describe("Login as a company and create job posting", () => {
    beforeEach(() => {
        cy.login('companyUsername', 'password', 'company')
    })

    it('passes', () => {
        cy.get('[data-testid="nav-dropdown"]').click()
        cy.get('[data-testid="nav-create-post"]').click()

        cy.get('[data-testid="work-name"]').type('Work name');
        cy.get('[data-testid="work-desc"]').type('Work description');
        cy.get('[data-testid="work-salary"]').type('2000');
        cy.get('[data-testid="work-claims"]').type('Some claims');
        cy.get('[data-testid="work-info"]').type('Very interesting job.');
        cy.get('[data-testid="work-expiry-date"]').type('2024-06-25');
        cy.get('[data-testid="form-submit"]').click()
    })
})