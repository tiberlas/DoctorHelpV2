import React from 'react'
import { Chart } from 'react-charts'

function ViewGraph(props) {

    const points = React.useMemo(
      () =>
      [
        {
          label: 'done appointments',
          data: props.graphPoints
        },
      ],
      [props.graphPoints]
    )
  
    const axes = React.useMemo(
        () => [
          { primary: true, type: 'time', position: 'bottom' },
          { type: 'linear', position: 'left' }
        ],
        []
      )
  
      const series = React.useMemo(
        () => ({
          type: 'area'
        }),
        [props.graphPoints]
      )

    return (
        <div
        class="jumbotron"

      style={{
        width: '800px',
        height: '600px'
      }}
    >
            <Chart data={points} series={series} axes={axes} tooltip dark/>
        </div>
    )
  }
  export default ViewGraph;