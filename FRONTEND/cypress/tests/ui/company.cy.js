describe("Test company functionality", () => {
    beforeEach(() => {
        cy.login('companyUsername', 'password', 'company')
    })

    it('Create job posting, verify, delete', () => {
        // creation
        let workName = 'Work name 241294151'
        cy.get('[data-testid="nav-dropdown"]').click()
        cy.get('[data-testid="nav-create-post"]').click()

        cy.get('[data-testid="work-name"]').type(workName);
        cy.get('[data-testid="work-desc"]').type('Work description');
        cy.get('[data-testid="work-salary"]').type('2000');
        cy.get('[data-testid="work-claims"]').type('Some claims');
        cy.get('[data-testid="work-info"]').type('Very interesting job.');
        cy.get('[data-testid="work-expiry-date"]').type('2024-06-25');
        cy.get('[data-testid="form-submit"]').click()

        // verification
        cy.get('[data-testid="nav-dropdown"]').click()
        cy.get('[data-testid="nav-home"]').click()
        cy.contains(workName)

        // deletion
        cy.contains(workName).parent().contains('Delete Post').click()
        cy.get('[data-testid="modal-delete"]').click();

    })

    // it('Create job posting', () => {
    //     cy.get('[data-testid="nav-dropdown"]').click()
    //     cy.get('[data-testid="nav-create-post"]').click()
    //
    //     cy.get('[data-testid="work-name"]').type('Work name');
    //     cy.get('[data-testid="work-desc"]').type('Work description');
    //     cy.get('[data-testid="work-salary"]').type('2000');
    //     cy.get('[data-testid="work-claims"]').type('Some claims');
    //     cy.get('[data-testid="work-info"]').type('Very interesting job.');
    //     cy.get('[data-testid="work-expiry-date"]').type('2024-06-25');
    //     cy.get('[data-testid="form-submit"]').click()
    // })
    //
    it('Update company profile', () => {
        cy.get('[data-testid="nav-end"]').click()
        cy.get('[data-testid="my-account"]').click()

        cy.url().should('contain', 'profile')

        cy.get('[data-testid="company-name"]').clear().type('CoolCompany')
        cy.get('[data-testid="address"]').clear().type('Tallinn 11')
        cy.get('[data-testid="submit"]').click()

        cy.get('[data-testid="user-name"]').should('contain', 'CoolCompany')
    })
})