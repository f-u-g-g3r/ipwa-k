/*
TODO
Create tests for authentication API, try all possible test cases(student, company, teacher, coordinator, wrong data);
Try to check all parts of the requests, try stubbing;
Try aliases;

First test case(example):
    - Make a request for the API with provided username and password (userUsername, password); !
    - Check response status; !
    - Check response body; !
    - Do something with response body; !
    - Check response headers; !
    - Save request as an aliases and try to do something with '.wait()' command;!
 */
const apiUrl = 'http://localhost:8080';
const russianRegex = /[а-яА-ЯёЁ]/;

describe('API authentication tests', () => {
    beforeEach(() => {
        // cy.request('POST', `${apiUrl}/auth/authenticate`, { username: 'userUsername', password: 'password' }).as("studentLogin")
    })

    it.only('Test request', () => {
        cy.intercept(`${apiUrl}/api/tests`, ["hello"]).as('test-request')

        cy.visit(`${apiUrl}/api/tests`)

        cy.wait('@test-request').then((interception) => {
            cy.log(interception.response.body)
        })
    })


    it('Should authenticate successfully as a student', () => {
        cy.get('@studentLogin').should((response) => {
            expect(response.body).to.have.property('id').that.not.null;
            expect(response.body).to.have.property('token').that.not.empty;
            expect(response.body).to.have.property('authority').that.deep.equals([{authority: 'STUDENT'}]);
        })
    })

    it('Check that token doesnt include any russian letters', () => {
        cy.get('@studentLogin').should((response) => {
            expect(response.body).to.have.property('token').that.not.match(russianRegex)
        })
    })

})