describe('Login', () => {
    context('Login as a student', () => {
        it('passes', () => {
            cy.login('userUsername', 'password', 'student')
        })
    })

    context('Login as a company', () => {
        it('passes', () => {
            cy.login('companyUsername', 'password', 'company')
        })
    })

    context('Login as a teacher', () => {
        it('passes', () => {
            cy.login('teacherUsername', 'password', 'teacher')
        })
    })

    context('Login as a coordinator', () => {
        it('passes', () => {
            cy.login('coordinatorEmail', 'password', 'coordinator')
        })
    })

    context('Login with wrong credentials', () => {
        it('passes', () => {
            cy.visit('/login')

            cy.get('input[name=username]').type('asfoasfpasfsfa')
            cy.get('input[name=password]').type(`asfasfasfasfsa{enter}`)

            cy.url().should('include', '/login')
            cy.get('[data-testid="user-name"]').should('contain', 'Guest')
        });
    })
})

