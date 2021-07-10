import React from 'react'
import './video.css'
import Video from './media.io_EKG.mp4'

class HomeLanding extends React.Component {

    render() {
        return (<div> 
                {/* IMAGE <header style={{
                        height: "100vh",
                        minHeight: "500px",
                        backgroundImage: "url('https://img.rawpixel.com/s3fs-private/rawpixel_images/website_content/228a-ake-8691.jpg?auto=format&bg=transparent&con=3&cs=srgb&dpr=1&fm=jpg&ixlib=php-3.1.0&mark=rawpixel-watermark.png&markalpha=90&markpad=13&markscale=10&markx=25&q=75&usm=15&vib=3&w=600&s=15c549c902f6b7f34d46d2fdc83460bd')",
                        backgroundSize: "cover",
                        backgroundPosition: "center",
                        backgroundRepeat: "no-repeat"
                      }
                }>
                    <div class="container h-100">
                        <div class="row h-100 align-items-center">
                        <div class="col-12 text-center">
                            <h1 class="font-weight-light">Vertically Centered Masthead Content</h1>
                            <p class="lead">A great starter layout for a landing page</p>
                        </div>
                        </div>
                    </div>
                </header> */}

            <header>
            <div class="overlay"></div>
            <video  autoplay="autoplay" muted="muted" loop="loop" controls>
                <source src={Video} type="video/mp4"/>
            </video>
            <div class="container h-100">
                <div class="d-flex h-100 text-center align-items-center">
                <div class="w-100 text-white">
                    <h1 class="display-3">We value life.</h1>
                    <p class="lead mb-0">Get help, at moments notice.</p>
                </div>
                </div>
            </div>
            </header>

        </div>)
    }
}

export default HomeLanding